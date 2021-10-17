package com.sp.fc.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Person {
    private String name;
    private int height;

    public boolean over(int pivot) {
        return height >= pivot;
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Horse {
    private String name;
    private int height;

    public boolean over(int pivot) {
        return height >= pivot;
    }
}

public class SpELTest {
    ExpressionParser parser = new SpelExpressionParser();
    Person person = Person.builder()
            .name("홍길동").height(178).build();

    Horse horse = Horse.builder()
            .name("nancy").height(160).build();

    @DisplayName("기본 테스트트")
    @Test
    void test_1() {
        assertEquals("홍길동",
                parser.parseExpression("name").getValue(person));
    }

    @DisplayName("값 변경")
    @Test
    void test_2() {
        parser.parseExpression("name").setValue(person, "호나우도");

        assertEquals("호나우도",
                parser.parseExpression("name").getValue(person));
    }

    @DisplayName("메서드 호출_1")
    @Test
    void test_3() {
        assertEquals(Boolean.TRUE,
                parser.parseExpression("over(178)").getValue(person, Boolean.class));
    }

    @DisplayName("메서드 호출_2")
    @Test
    void test_4() {
        assertEquals(Boolean.FALSE,
                parser.parseExpression("over(170)").getValue(horse, Boolean.class));
    }

    @DisplayName("Context 테스트_1")
    @Test
    void test_5() {
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        ctx.setBeanResolver(new BeanResolver() {
            @Override
            public Object resolve(EvaluationContext context, String beanName) throws AccessException {
                return beanName.equals("person") ? person : horse;
            }
        });

        assertEquals(Boolean.TRUE,
                parser.parseExpression("@person.over(178)").getValue(ctx, Boolean.class));

        assertEquals(Boolean.FALSE,
                parser.parseExpression("@horse.over(170)").getValue(ctx, Boolean.class));
    }

    @DisplayName("Context 테스트_2")
    @Test
    void test_6() {
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        ctx.setBeanResolver(new BeanResolver() {
            @Override
            public Object resolve(EvaluationContext context, String beanName) throws AccessException {
                return beanName.equals("person") ? person : horse;
            }
        });

        ctx.setRootObject(person);
        assertEquals(Boolean.TRUE,
                parser.parseExpression("over(178)").getValue(ctx, Boolean.class));

        ctx.setVariable("horse", horse);
        assertEquals(Boolean.FALSE,
                parser.parseExpression("#horse.over(170)").getValue(ctx, Boolean.class));
    }
}