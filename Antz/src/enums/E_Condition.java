package enums;

import java.util.EnumSet;

/**
 * Enumerates #condition# for the Sense instruction in the Ant-Brain state machine.
 * @author JOH
 *
 */
public enum E_Condition {

	FRIEND,
	FOE,
	FRIENDWITHFOOD,
	FOEWITHFOOD,
	FOOD,
	ROCK,
	MARKER,
	FOEMARKER,
	HOME,
	FOEHOME;
	
	/**
	 * Returns an EnumSet of all valid enums.
	 * @return the EnumSet
	 */
	public static EnumSet<E_Condition> valid() {
		return EnumSet.allOf(E_Condition.class);
	}
}
