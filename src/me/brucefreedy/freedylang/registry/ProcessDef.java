package me.brucefreedy.freedylang.registry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.brucefreedy.freedylang.impl.ConsoleLogging;
import me.brucefreedy.freedylang.impl.Millisec;
import me.brucefreedy.freedylang.impl.NanoTime;
import me.brucefreedy.freedylang.impl.TestCode;
import me.brucefreedy.freedylang.lang.Breaker;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.Skipper;
import me.brucefreedy.freedylang.lang.arithmetic.Arithmetic;
import me.brucefreedy.freedylang.lang.arithmetic.increase.DecrementImpl;
import me.brucefreedy.freedylang.lang.arithmetic.increase.IncrementImpl;
import me.brucefreedy.freedylang.lang.body.brace.EndBrace;
import me.brucefreedy.freedylang.lang.body.brace.FrontBrace;
import me.brucefreedy.freedylang.lang.body.bracket.EndBracket;
import me.brucefreedy.freedylang.lang.body.bracket.FrontBracket;
import me.brucefreedy.freedylang.lang.body.parenthesis.EndParenthesis;
import me.brucefreedy.freedylang.lang.body.parenthesis.FrontParenthesis;
import me.brucefreedy.freedylang.lang.comment.MultiComment;
import me.brucefreedy.freedylang.lang.comment.SingleComment;
import me.brucefreedy.freedylang.lang.control.branch.ReturnImpl;
import me.brucefreedy.freedylang.lang.control.conditional.*;
import me.brucefreedy.freedylang.lang.control.iteration.ForImpl;
import me.brucefreedy.freedylang.lang.control.iteration.WhileImpl;
import me.brucefreedy.freedylang.lang.quotation.DoubleQuotation;
import me.brucefreedy.freedylang.lang.quotation.SingleQuotation;
import me.brucefreedy.freedylang.lang.variable.*;

import java.util.function.Supplier;

@Getter
@AllArgsConstructor
public enum     ProcessDef {
    SKIP(Skipper::new),
    BREAK(Breaker::new),
    CONSOLE_LOGGING(ConsoleLogging::new),
    DOUBLE_QUO(DoubleQuotation::new),
    SINGLE_QUO(SingleQuotation::new),
    FRONT_PARENTHESIS(FrontParenthesis::new),
    END_PARENTHESIS(EndParenthesis::new),
    FRONT_BRACE(FrontBrace::new),
    END_BRACE(EndBrace::new),
    FRONT_BRACKET(FrontBracket::new),
    END_BRACKET(EndBracket::new),
    EQUALS(Comparison.Equals::new),
    NOT_EQUALS(Comparison.NotEquals::new),
    BIGGER(Comparison.Bigger::new),
    EQUALS_BIGGER(Comparison.EqBigger::new),
    SMALLER(Comparison.Smaller::new),
    EQUALS_SMALLER(Comparison.EqSmaller::new),
    TRUE(Comparison.True::new),
    FALSE(Comparison.False::new),
    REVERSE_BOOL(Comparison.ReverseBool::new),
    IF(IfImpl::new),
    ELSE(Else::new),
    AND(And::new),
    OR(Or::new),
    ASSIGNMENT(Assignment::new),
    SIMPLE_NULL(SimpleNull::new),
    CLASS(VariableImpl::new),
    MEMBER(Member::new),
    TEST_CODE(TestCode::new),
    RETURN(ReturnImpl::new),
    ADDITION(Arithmetic.Addition::new),
    SUBTRACT(Arithmetic.Subtract::new),
    MULTIPLY(Arithmetic.Multiply::new),
    SQUARED(Arithmetic.Squared::new),
    DIVIDE(Arithmetic.Divide::new),
    REMAINDER(Arithmetic.Remainder::new),
    SINGLE_COMMENT(SingleComment::new),
    MULTI_COMMENT(MultiComment::new),
    WHILE(WhileImpl::new),
    NEW(AbstractInstance::new),
    INCREMENT(IncrementImpl::new),
    DECREMENT(DecrementImpl::new),
    FOR(ForImpl::new),
    MILLI_SEC(Millisec::new),
    NANO_TIME(NanoTime::new),
    ;
    private final Supplier<Process<?>> processSupplier;
}
