/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 * <p/>
 * scsc
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

package sk.tuke.yolkfolk.actors.characters.monkey;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Item;
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.actors.AbstractActor;
import sk.tuke.yolkfolk.actors.State;
import sk.tuke.yolkfolk.actors.Stateful;
import sk.tuke.yolkfolk.actors.player.players.dizzy.Dizzy;
import sk.tuke.yolkfolk.collectables.Key;
import sk.tuke.yolkfolk.spaces.MonkeyStoneSpace;

/**
 * TODO: implement some monkey game (will unlock secret part, e.g. in a well)
 * <p/>
 * Created by Steve on 3.12.2015.
 */
public class Monkey extends AbstractActor implements Item, Stateful
{
	//Constants
	public static final String NAME = "Monkey";
	protected static final int NUMBER_OF_STATES = 4;
	//Vzdialenost v nasobkoch svojich rozmerov, na ktoru je schopny detegovat hraca
	protected static final float DETECTION_RADIUS = 2.0f;
	//Pocitadlo cyklov, ktore musia prejst pred spustenim vyzvy pre stav 0
	protected static final int STATE_0_COUNTER = 250;
	//Pocet vtakov, ktore musi Dizzy najst
	protected static final int BIRDS_REQUIRED = 3;
	//Pocet kamenov, ktore musi Dizzy najst
	protected static final int STONES_REQUIRED = 3;

	//Variables
	//Pocitadlo cyklov, iba z estetickeho dovodu pre dlzku spravy
	private int counter;
	//Pocitadlo kamenov
	private int stones;

	//Aktualny stav opice
	private State state;

	//Objects
	private Dizzy firstPlayer;
	private Message braveHeroNotice;
	private MonkeyStoneSpace stoneSpace;

	//Inicializacia
	{
		setState(defaultState());
		this.counter = 0;
		this.stones = 0;
		this.firstPlayer = null;
		this.braveHeroNotice = null;
		this.stoneSpace = null;
	}

	public Monkey()
	{
		super(Monkey.NAME, "sprites/monkey.png", 48, 32);
	}

	//Monkey gives player a key
	public void dispatchKey()
	{
		Key key = new Key();
		key.setPosition(getX() - key.getWidth(), getY() - key.getHeight());
		getWorld().addActor(key);
	}

	//Do not call this method :) Like, ever.
	public void cheatMe()
	{
		setState(new DispatchingReward(this));
	}

	//Nastavenie noveho stavu opice
	public void setState(State state)
	{
		this.state = state;
	}
	public State getState()
	{
		return this.state;
	}
	//Zaciatocny stav, v ktorom sa opica objavi vo svete
	public State defaultState()
	{
		return new WaitingForPlayer(this);
	}

	protected Dizzy getDizzy()
	{
		return this.firstPlayer;
	}
	protected void setDizzy(Dizzy firstPlayer)
	{
		this.firstPlayer = firstPlayer;
	}
	public void setCounter(int counter)
	{
		this.counter = counter;
	}
	public int getCounter()
	{
		return counter;
	}
	public int getStones()
	{
		return stones;
	}
	public void setStones(int stones)
	{
		this.stones = stones;
	}
	public MonkeyStoneSpace getStoneSpace()
	{
		return stoneSpace;
	}
	public void setStoneSpace(MonkeyStoneSpace stoneSpace)
	{
		this.stoneSpace = stoneSpace;
	}

	public void removeMessage()
	{
		if (this.braveHeroNotice != null)
		{
			this.braveHeroNotice.remove();
		}
	}
	public Message newMessage(String title, String message, Actor actor)
	{
		removeMessage();
		this.braveHeroNotice = new Message(title, message, actor);
		return this.braveHeroNotice;
	}
	public Message newMessage(String title, String message)
	{
		return newMessage(title, message, this);
	}

	@Override
	public void act()
	{
		//Vykona akcie reprezentujuce aktualny stav opice.
		getState().act();
	}

	/*@Override
	public void addedToWorld(World world)
	{
		super.addedToWorld(world);
	}*/
}
