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

package sk.tuke.yolkfolk.actors.player.dizzy;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Animation;
import sk.tuke.yolkfolk.actors.Cursable;
import sk.tuke.yolkfolk.actors.CurseEvent;
import sk.tuke.yolkfolk.actors.Greeter;
import sk.tuke.yolkfolk.actors.player.AbstractPlayer;
import sk.tuke.yolkfolk.actors.player.states.PlayerState;

/**
 * Dizzy, nas prvy hlavny hrdina.
 * A generic Player.
 *
 * Created by Steve on 9.11.2015.
 */
public class Dizzy extends AbstractPlayer implements Cursable
{
	//Premenne
	private boolean cursed;

    public Dizzy()
    {
		//Nastavenie animacii
        super("Dizzy","sprites/dizzy.png",25,25);
        setAnimationLeft(new Animation("sprites/dizzy_left.png",25,25));
		setAnimationRight(new Animation("sprites/dizzy_right.png",25,25));
		setAnimationJump(new Animation("sprites/dizzy_jump.png", 25, 25));
		setAnimationJumpLeft(new Animation("sprites/dizzy_jump_left.png",25,25));
		setAnimationJumpRight(new Animation("sprites/dizzy_jump_right.png",25,25));

		//Dlzka kroku Dizzyho - jeho rychlost a vyska skoku
		setStep(1.75f);
		setJumpHeight(45f);

		//Parametre tykajuce sa padania
		setFallLimit(200);
		setFallDamageRatio(2);

		//Inicializacia vedlajsich premennych
		this.cursed = false;
    }

    @Override
    public void act()
	{
		//Vykonaj act, ktory ma vykonat kazdy Player
		super.act();
	}

	//Operacie s ostatnymi actormi vo svete, ktorych sa Player dotyka.
	@Override
	protected void actOnIntersect(Actor actor)
	{
		//Pozdrav Daisy alebo ineho zdravica
		if(actor instanceof Greeter)
		{
			Greeter greeter = (Greeter) actor;
			greeter.greetPlayer(this);
		}
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
		if(state instanceof DizzyState)
		{
			super.setState(state);
		}
	}
	@Override
	public DizzyState getState()
	{
		return (DizzyState)super.getState();
	}
	@Override
	public DizzyState defaultState()
	{
		return new Standing(this);
	}
}
