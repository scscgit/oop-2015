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

package sk.tuke.yolkfolk.actors.player.players.dizzy;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Animation;
import sk.tuke.yolkfolk.actors.objects.Well;
import sk.tuke.yolkfolk.actors.player.Player;
import sk.tuke.yolkfolk.actors.player.states.PlayerStanding;

/**
 * Dizzy Standing state.
 * <p/>
 * Created by Steve on 15.12.2015.
 */
public class Standing extends PlayerStanding implements DizzyState
{
	public Standing(Player player)
	{
		super(player, new Animation("sprites/dozy.png", 22, 15));
	}

	//Dizzy ma custom akciu pohybu smerom dole
	@Override
	protected void keyboardActions()
	{
		//Ak Dizzy dohral hru s opicou a stlacil klavesu dodola
		if (input().downwardNotUpward() && getPlayer() instanceof Dizzy && ((Dizzy) getPlayer()).isMonkeyGameDone())
		{
			//Ak najde studnu, na ktorej aktualny hrac stoji, tak tu studnu odstrani a bude sa do nej dat skocit
			for (Actor actor : getPlayer().getWorld())
			{
				if (actor instanceof Well)
				{
					Well well = (Well) actor;
					if (well.intersectsAbove(getPlayer(), getPlayer().getHeight() / 2))
					{
						getPlayer().getWorld().removeActor(well);
						break;
					}
				}
			}
		}
		else
		{
			super.keyboardActions();
		}
	}
}
