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

import sk.tuke.yolkfolk.actions.player.Cheats;
import sk.tuke.yolkfolk.actions.player.Exit;
import sk.tuke.yolkfolk.actions.player.Use;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * Trieda definujuca spolocne funkcionality pre stavy, v ktorych sa Player nachadza na zemi.
 * <p/>
 * Created by Steve on 26.12.2015.
 */
public abstract class AbstractGroundState extends AbstractPlayerState
{
	public AbstractGroundState(Player player)
	{
		super(player);
	}

	//Akcie (zmeny stavu), ktore sa maju vykonat na zaklade vstupu z klavesnice, kym je hrac na zemi
	protected abstract void keyboardActions();

	@Override
	public void act()
	{
		//Vykona vsetky zakladne operacie
		resetActions().addAction(new Use()).addAction(new Exit()).addAction(new Cheats()).runActions();

		//Ak hrac zomrel, tak nastavim prislusny stav
		if (isPlayerDead())
		{
			interpret("set state dying");
		}
		//Ak je vo vzduchu, zacne padat
		else if (isPlayerFalling())
		{
			interpret("set state falling");
		}
		//Ak je na zemi tak vyskusa, ci je potrebne zmenit stav
		else
		{
			keyboardActions();
		}
	}
}
