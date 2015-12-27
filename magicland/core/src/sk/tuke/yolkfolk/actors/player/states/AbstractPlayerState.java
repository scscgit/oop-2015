/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 * <p/>
 * Stefan Ciberaj, ZS 2015/2016
 * Technicka univerzita v Kosiciach, Fakulta elektrotechniky a informatiky
 * <p/>
 * Licencia: Volny softver, Open-Source GNU GPL v3+
 * Vseobecna verejna licencia. Program je dovolene volne sirit a upravovat.
 * Upraveny program / cast programu moze ktokolvek vyuzit ako na osobne,
 * tak aj komercne ucely, ale nemoze ho vydat s vlastnym copyrightom,
 * ktory nie je kompatibilny s GNU GPL v3+.
 * gnu.org/licenses/gpl-faq.html
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see < http://www.gnu.org/licenses/ >.
 */

package sk.tuke.yolkfolk.actors.player.states;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.PhysicsHelper;
import sk.tuke.yolkfolk.actions.ActionQueue;
import sk.tuke.yolkfolk.actors.player.Player;
import sk.tuke.yolkfolk.input.CustomInput;

/**
 * Abstract implementation for each of the Player States.
 * Copies methods for an easy queue of Actions.
 * <p/>
 * Created by Steve on 11.12.2015.
 */
public abstract class AbstractPlayerState extends ActionQueue<Actor, Void> implements PlayerState
{
	//Objects
	private Player player;
	//private PlayerInterpreter interpreter;

	public AbstractPlayerState(Player player)
	{
		this.player = player;
		//this.interpreter = new PlayerInterpreter(player);
	}

	//Odvodene triedy implementuju svoju animaciu
	//protected abstract void updateAnimation();

	//Ziska referenciu na hraca, ktoreho stav sa nastavuje
	public Player getPlayer()
	{
		return this.player;
	}

	//Runs all linked actions on player
	@Override
	public Void runActions()
	{
		return runActions(getPlayer());
	}

	//Nastavi horizontalnu rychlost hraca na pohyb v danom smere
	protected void setVelocity(PlayerState.Direction direction)
	{
		if (direction == PlayerState.Direction.LEFT)
		{
			PhysicsHelper.setLinearVelocity(getPlayer(), -getPlayer().getStep(),
			                                PhysicsHelper.getLinearVelocity(getPlayer())[1]);
		}
		else if (direction == PlayerState.Direction.RIGHT)
		{
			PhysicsHelper
				.setLinearVelocity(getPlayer(), getPlayer().getStep(), PhysicsHelper.getLinearVelocity(getPlayer())[1]);
		}
		//Possibly can add option of direction UP later, but my design works in the opposite way
	}

	//Zastavi horizontalny pohyb, vyzadovane napriklad pred nastavenim posobenia sily
	protected void stopVelocity(boolean keepVertical)
	{
		PhysicsHelper
			.setLinearVelocity(getPlayer(), 0f, keepVertical ? PhysicsHelper.getLinearVelocity(getPlayer())[1] : 0f);
	}

	//Overload, implicitna odpoved na otazku, ci pri zastaveni horizontalneho pohybu ponechat vertikalny poheb
	protected void stopVelocity()
	{
		stopVelocity(false);
	}

	//Skontroluje, ci je hrac mrtvy
	protected boolean isPlayerDead()
	{
		return getPlayer().getEnergy() == 0;
	}

	//Odvodeny stav ma povinnost implementovat act
	@Override
	public abstract void act();

	//Kazdy stav meni stav hraca cez metody triedy typu PlayerStates
	//Odporuca sa prestat pouzivat tuto zastaralu (ale funkcnu) metodu a prejst na PlayerInterpreter
	@Deprecated
	protected PlayerStates changeState()
	{
		return getPlayer().changeState();
	}

	//Interpretuje prikaz pre hraca
	protected void interpret(String commands)
	{
		getPlayer().interpret(commands);
	}

	//Vrati velkost rychlosti hraca v smere Y
	protected float speedY()
	{
		return Math.abs(PhysicsHelper.getLinearVelocity(getPlayer())[1]);
	}

	//Aktualizuje animaciu vzhladom na smer hraca, metoda bola vytvorena z dovodu DRY principu
	protected void updateAnimDir(PlayerState.Direction direction)
	{
		if (direction == PlayerState.Direction.LEFT)
		{
			getPlayer().runAnimationLeft();
			getPlayer().stopAnimationRight();
		}
		else if (direction == PlayerState.Direction.RIGHT)
		{
			getPlayer().stopAnimationLeft();
			getPlayer().runAnimationRight();
		}
	}

	//Skontroluje, ci hrac fyzicky pada, podla coho sa odporuca nastavit stav na PlayerFalling
	protected boolean isPlayerFalling()
	{
		return !getPlayer().standsOnSolid() &&
		       PhysicsHelper.getLinearVelocity(getPlayer())[1] < Player.VELOCITY_Y_FALL;
	}

	public CustomInput input()
	{
		return getPlayer().getPlayerInput();
	}
}
