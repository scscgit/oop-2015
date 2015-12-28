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

package sk.tuke.yolkfolk.spaces;

import sk.tuke.yolkfolk.GameMusic;
import sk.tuke.yolkfolk.NewWorldOrder;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * Secret.
 * <p/>
 * Created by Steve on 27.12.2015.
 */
public class SecretZone extends FlyZone
{
	//Constants
	public static final String NAME = "SecretZone";

	public SecretZone()
	{
		super(SecretZone.NAME);
	}

	//Akcie vykonane po dotyku s hracom
	@Override
	protected void playerIntersects(Player player)
	{
		super.playerIntersects(player);
		if (player.getWorld() instanceof NewWorldOrder)
		{
			final NewWorldOrder world = (NewWorldOrder) player.getWorld();
			//Nastavi tajnu hudbu
			world.loadMusic(GameMusic.getSecretPath());
		}
	}
}
