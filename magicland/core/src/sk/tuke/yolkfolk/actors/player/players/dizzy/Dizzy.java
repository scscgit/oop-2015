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

package sk.tuke.yolkfolk.actors.player.players.dizzy;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Animation;
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.GameMusic;
import sk.tuke.yolkfolk.actors.Cursable;
import sk.tuke.yolkfolk.actors.CurseEvent;
import sk.tuke.yolkfolk.actors.Greeter;
import sk.tuke.yolkfolk.actors.player.AbstractPlayer;
import sk.tuke.yolkfolk.actors.player.states.PlayerState;
import sk.tuke.yolkfolk.actors.player.states.PlayerStates;

/**
 * Dizzy, nas prvy hlavny hrdina.
 * <p/>
 * A generic Player. Can play the Monkey game.
 * <p/>
 * Created by Steve on 9.11.2015.
 */
public class Dizzy extends AbstractPlayer implements Cursable
{
	//Constants
	public static final String NAME = "Dizzy";

	//Variables
	private boolean cursed;
	private int birdsCaught;
	private boolean monkeyFinished;
	//private boolean initialized;

	public Dizzy() //TODO be last actor created on map
	{
		//Nastavenie animacii
		super(Dizzy.NAME, "sprites/dizzy.png", 25, 25);
		setAnimationLeft(new Animation("sprites/dizzy_left.png", 25, 25));
		setAnimationRight(new Animation("sprites/dizzy_right.png", 25, 25));
		setAnimationJump(new Animation("sprites/dizzy_jump.png", 25, 25));
		setAnimationJumpLeft(new Animation("sprites/dizzy_jump_left.png", 25, 25));
		setAnimationJumpRight(new Animation("sprites/dizzy_jump_right.png", 25, 25));

		//Dlzka kroku Dizzyho - jeho rychlost a vyska skoku
		setStep(1.5f);
		setJumpHeight(45f);

		//Parametre tykajuce sa padania
		//Kriticka rychlost, akou musi Dizzy padat, aby mu bolo udelene poskodenie z padu. Pri JumpHeight 45f nastava spontanne rychlost pred dopadom okolo 3.5f.
		setFallLimit(4.20f);
		//Koeficient, ktorym sa vynasobi rozdiel rychlosti padania a kritickej rychlosti pred udelenim zranenia o rovnakej hodnote.
		setFallDamageRatio(30);

		//Inicializacia vedlajsich premennych
		this.cursed = false;
		this.birdsCaught = 0;
		this.monkeyFinished = false;
		//this.initialized = false;
	}

	/*@Override
	public void act()
	{
		if(!this.initialized)
		{
			this.initialized = true;
		}

		//Vykonaj act, ktory ma vykonat kazdy Player
		super.act();
	}*/

	//Operacie s ostatnymi actormi vo svete, ktorych sa Player dotyka.
	@Override
	protected void actOnIntersect(Actor actor)
	{
		//Pozdrav Daisy alebo ineho zdravica
		if (actor instanceof Greeter)
		{
			Greeter greeter = (Greeter) actor;
			greeter.greetPlayer(this);
		}
	}

	//Ked Dizzy chyti vtaka, tak si ho pripocita
	public void catchBird()
	{
		this.birdsCaught++;
	}
	public int getCaughtBirds()
	{
		return this.birdsCaught;
	}

	//Metoda na dobrovolne prijatie kliatby (napr. autosugesciou), pripadne od ineho actora
	public void beCursed(Actor curser)
	{
		this.cursed = true;

		//Upozorni kazdeho na to, ze bol prekliaty
		CurseEvent.notifyObservers(this);
	}

	//Dizzy je prekliaty iba dovtedy, kym tomu veri (kym si to pamata)
	public boolean isCursed()
	{
		return this.cursed;
	}

	//Nastavovanie noveho stavu
	@Override
	public void setState(PlayerState state)
	{
		if (state instanceof DizzyState)
		{
			super.setState(state);
		}
	}
	@Override
	public DizzyState getState()
	{
		return (DizzyState) super.getState();
	}
	@Override
	public DizzyState defaultState()
	{
		return new Standing(this);
	}
	//Access to list of available states
	@Override
	public PlayerStates changeState()
	{
		return new DizzyStates(this);
	}

	//Po dokonceni serie hier s opicou sa spusti nasledujuca metoda
	public void setMonkeyGameDone()
	{
		this.monkeyFinished = true;
	}
	public boolean isMonkeyGameDone()
	{
		return this.monkeyFinished;
	}

	//Operations that happen after Dizzy dies
	@Override
	public void onDeath()
	{
		GameMusic.playGameOver();
	}

	//Last wish of Dizzy after he wins the game and Daisy
	public void wonTheGame()
	{
		interpret("set state frozen");
		new Message("Congratulations, you've won this game!",
		            "Together with Daisy, they'll\n" + "both live happily ever after.",
		            this);
	}
}
