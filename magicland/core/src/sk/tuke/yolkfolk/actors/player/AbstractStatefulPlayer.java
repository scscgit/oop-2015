/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 *
 * Stefan Ciberaj, ZS 2015/2016
 * Technicka univerzita v Kosiciach, Fakulta elektrotechniky a informatiky
 *
 * Licencia: Volny softver, Open-Source GNU GPL v3+
 * Vseobecna verejna licencia. Program je dovolene volne sirit a upravovat.
 * Upraveny program / cast programu moze ktokolvek vyuzit ako na osobne,
 * tak aj komercne ucely, ale nemoze ho vydat s vlastnym copyrightom,
 * ktory nie je kompatibilny s GNU GPL v3+.
 * gnu.org/licenses/gpl-faq.html
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see < http://www.gnu.org/licenses/ >.
 */

package sk.tuke.yolkfolk.actors.player;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.World;
import sk.tuke.yolkfolk.actors.player.states.PlayerState;
import sk.tuke.yolkfolk.actors.player.states.PlayerStates;
import sk.tuke.yolkfolk.input.CustomInput;
import sk.tuke.yolkfolk.interpreter.PlayerInterpreter;

/**
 * Ku podpore fyzickeho pohybu vo vzduchu pridava podporu stavov a niektore vedlajsie metody hraca.
 *
 * Created by Steve on 28.12.2015.
 */
public abstract class AbstractStatefulPlayer extends AbstractAirbornePlayer
{
	//Objects
	private PlayerState currentState;
	private CustomInput playerInput;

	public AbstractStatefulPlayer(String name, String animationString, int animationX, int animationY)
	{
		super(name, animationString, animationX, animationY);
	}

	//Operacie s ostatnymi actormi vo svete, ktorych sa Player dotyka. Ocakava sa implementacia funkcie.
	protected abstract void actOnIntersect(Actor actor);

	//Each actor must implement his default state
	public abstract PlayerState defaultState();

	//Nastavovanie noveho stavu
	public void setState(PlayerState state)
	{
		this.currentState = state;
	}
	public PlayerState getState()
	{
		if (this.currentState == null)
		{
			this.currentState = defaultState();
		}
		return this.currentState;
	}
	//Access to the list of all available states
	public PlayerStates changeState()
	{
		return new PlayerStates(this);
	}

	//Interprets command for this Player
	@Override
	public void interpret(String commands)
	{
		new PlayerInterpreter(this).interpret(commands);
	}

	//Each player is egocentric - he thinks the World, literally, revolves around him.
	@Override
	public void addedToWorld(World world)
	{
		super.addedToWorld(world);
		world.centerOn(this);
	}

	//Keyboard input of the player
	public CustomInput getPlayerInput()
	{
		return this.playerInput;
	}
	public void setPlayerInput(CustomInput playerInput)
	{
		this.playerInput = playerInput;
	}
}
