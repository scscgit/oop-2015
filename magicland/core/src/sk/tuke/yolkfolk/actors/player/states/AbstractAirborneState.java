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

package sk.tuke.yolkfolk.actors.player.states;

import sk.tuke.gamelib2.PhysicsHelper;
import sk.tuke.yolkfolk.actions.player.Cheats;
import sk.tuke.yolkfolk.actions.player.Exit;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * Trieda definujuca spolocne funkcionality pre stavy, v ktorych sa Player nachadza vo vzduchu.
 * <p/>
 * Created by Steve on 17.12.2015.
 */
public abstract class AbstractAirborneState extends AbstractPlayerState
{
	//Constants
	private static final int MIN_TIME_JMP = 4;

	//Variables
	private int timeAfterJump;
	private float lastSpeedY;

	public AbstractAirborneState(Player player)
	{
		super(player);
		this.timeAfterJump = 0;
		resetLastSpeedY();
	}

	//Podla stlacenej klavesy umozni dodatocne skoky z dovodu lietania
	protected void fly()
	{
		if (input().upward())
		{
			interpret("set state jumping up");
		}
		if (input().left())
		{
			setVelocity(PlayerState.Direction.LEFT);
		}
		else if (input().right())
		{
			setVelocity(PlayerState.Direction.RIGHT);
		}
	}

	//Vrati predchadzajucu hodnotu velkosti rychlosti hraca v smere Y
	protected final float getLastSpeedY()
	{
		return this.lastSpeedY;
	}
	protected final void resetLastSpeedY()
	{
		this.lastSpeedY = speedY();
	}

	//Zisti, ci bol hrac dost dlho vo vzduchu, uzitocne na zistovanie ci pada alebo je na zemi
	protected final boolean enoughTimeInAir()
	{
		return this.timeAfterJump >= AbstractAirborneState.MIN_TIME_JMP;
	}

	@Override
	public void act()
	{
		//Vykona pozadovane akcie s vynimkou pouzitia predmetu
		resetActions().addAction(new Exit()).addAction(new Cheats()).runActions();

		//Zisti novu rychlost v smere Y pre pripadne vypocty
		resetLastSpeedY();

		//Necha hraca vykonat svoje operacie po novom pohybe
		getPlayer().afterMovement();

		//Ak hrac zomrel, tak nastavim prislusny stav
		if (isPlayerDead())
		{
			interpret("set state dying");
		}
		//Zvysne pripady, v ktorych sa hrac stale nachadza vo vzduchu
		else
		{
			//Ak nedopadol na zem, overi sa pripad lietania
			if (getPlayer().isFlyable())
			{
				fly();
			}
			else
			{
				//Priebezne oznamuje hracovi, akou rychlostou pada pre vypocet sily dopadu
				getPlayer().fall(PhysicsHelper.getLinearVelocity(getPlayer())[1]);
			}

			//Pred dovolenim nastavenia stavu na Standing najprv musi napocitat pomocnu hodnotu, aby vzdy skocil aspon trochu
			if (this.timeAfterJump < AbstractAirborneState.MIN_TIME_JMP)
			{
				this.timeAfterJump++;
			}
		}
	}
}
