package mightypork.gamecore.gui;


import java.util.HashSet;
import java.util.Set;

import mightypork.utils.interfaces.Enableable;


/**
 * A group of enableable objects that propagates it's "enable" state to them
 * all.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class ActionGroup implements Enableable {

	private boolean enabled = true;

	private final Set<Enableable> groupMembers = new HashSet<>();


	@Override
	public void setEnabled(boolean yes)
	{
		enabled = yes;
		for (final Enableable e : groupMembers)
			e.setEnabled(yes);
	}


	@Override
	public boolean isEnabled()
	{
		return enabled;
	}


	/**
	 * Add an {@link Enableable} to the group
	 *
	 * @param member the object to add
	 */
	public void add(Enableable member)
	{
		groupMembers.add(member);
	}


	/**
	 * Remove a group member
	 *
	 * @param member the object to remove
	 */
	public void remove(Enableable member)
	{
		groupMembers.remove(member);
	}

}
