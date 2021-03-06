/**
 * 
 */
package claire.hrt.wiki.commons;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import claire.hrt.wiki.commons.Condition;
import claire.hrt.wiki.commons.Null;
import claire.hrt.wiki.commons.except.InvariantViolationException;
import claire.hrt.wiki.commons.except.PostconditionViolationException;
import claire.hrt.wiki.commons.except.PreconditionViolationException;

/**
 * @author Claire
 */
public class TestCondition {
	
	/**
	 * Tests all conditions
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testConditions()
	{
		Condition.PRE.nonNull("", new Object());
		Condition.PRE.negative("", -1);
		Condition.PRE.positive("", 1);
		Condition.PRE.nonzero("", 1);
		Condition.PRE.notEmpty("", Null.nonNull(Arrays.asList(5)));
		Condition.PRE.empty("", new ArrayList<>());
		Condition.PRE.contains("", Null.nonNull(Arrays.asList(5)), 5);
		

		assertThrows(PreconditionViolationException.class, () -> { Condition.PRE.nonNull("", null); });
		assertThrows(PreconditionViolationException.class, () -> { Condition.PRE.negative("", 1); });
		assertThrows(PreconditionViolationException.class, () -> { Condition.PRE.positive("", -1); });
		assertThrows(PreconditionViolationException.class, () -> { Condition.PRE.nonzero("", 0); });
		assertThrows(PreconditionViolationException.class, () -> { Condition.PRE.notEmpty("", new ArrayList<>()); });
		assertThrows(PreconditionViolationException.class, () -> { Condition.PRE.empty("", Null.nonNull(Arrays.asList(5))); });
		assertThrows(PreconditionViolationException.class, () -> { Condition.PRE.contains("", Null.nonNull(Arrays.asList(5)), 6); });
		
		assertThrows(PostconditionViolationException.class, () -> { Condition.POST.nonNull("", null); });
		assertThrows(PostconditionViolationException.class, () -> { Condition.POST.negative("", 1); });
		assertThrows(PostconditionViolationException.class, () -> { Condition.POST.positive("", -1); });
		assertThrows(PostconditionViolationException.class, () -> { Condition.POST.nonzero("", 0); });
		assertThrows(PostconditionViolationException.class, () -> { Condition.POST.notEmpty("", new ArrayList<>()); });
		assertThrows(PostconditionViolationException.class, () -> { Condition.POST.empty("", Null.nonNull(Arrays.asList(5))); });
		assertThrows(PostconditionViolationException.class, () -> { Condition.POST.contains("", Null.nonNull(Arrays.asList(5)), 6); });
		
		assertThrows(InvariantViolationException.class, () -> { Condition.INVARIANT.nonNull("", null); });
		assertThrows(InvariantViolationException.class, () -> { Condition.INVARIANT.negative("", 1); });
		assertThrows(InvariantViolationException.class, () -> { Condition.INVARIANT.positive("", -1); });
		assertThrows(InvariantViolationException.class, () -> { Condition.INVARIANT.nonzero("", 0); });
		assertThrows(InvariantViolationException.class, () -> { Condition.INVARIANT.notEmpty("", new ArrayList<>()); });
		assertThrows(InvariantViolationException.class, () -> { Condition.INVARIANT.empty("", Null.nonNull(Arrays.asList(5))); });
		assertThrows(InvariantViolationException.class, () -> { Condition.INVARIANT.contains("", Null.nonNull(Arrays.asList(5)), 6); });
		
	}

}
