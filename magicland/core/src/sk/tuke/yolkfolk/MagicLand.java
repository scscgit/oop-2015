/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 *
 * scsc
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

package sk.tuke.yolkfolk;

import sk.tuke.gamelib2.World;
import sk.tuke.gamelib2.WorldScreen;

import sk.tuke.yolkfolk.actors.*;
import sk.tuke.yolkfolk.actors.items.*;
import sk.tuke.yolkfolk.actors.player.dizzy.Dizzy;
import sk.tuke.yolkfolk.collectables.Key;
import sk.tuke.yolkfolk.collectables.Apple;
import sk.tuke.yolkfolk.actors.items.Diamond;

/**
 * Welcome to MagicLand! Let the hate flow...
 *
 * Created by Steve on 9.11.2015.
 */
public class MagicLand extends WorldScreen
{
	private World world;

	@Override
	public void show()
	{
		world = new World();
		world.setMap("cviko2.tmx");

		Fire fire = new Fire();
		fire.setPosition(48,80);
		world.addActor(fire);


		Daisy daisy = new Daisy();
		daisy.setPosition(200,15);
		world.addActor(daisy);

		Apple apple = new Apple();
		apple.setPosition(150,15);
		world.addActor(apple);

		Apple apple2 = new Apple();
		apple2.setPosition(170,15);
		world.addActor(apple2);

		Diamond diamond = new Diamond("green");
		diamond.setPosition(20,15);
		world.addActor(diamond);

		Diamond diamond2 = new Diamond("silver");
		diamond2.setPosition(60,15);
		world.addActor(diamond2);

		Elevator elevator = new Elevator();
		elevator.setPosition(495,16);
		elevator.setEnd(120f);
		world.addActor(elevator);

		Lever lever = new Lever(elevator);
		lever.setPosition(465,16);
		world.addActor(lever);

		Lever lever2 = new Lever(elevator);
		lever2.setPosition(435,16);
		world.addActor(lever2);

		Devil devil = new Devil();
		devil.setPosition(605,145);
		world.addActor(devil);

		Key key = new Key();
		key.setPosition(400,16);
		world.addActor(key);

		Key key2 = new Key();
		key2.setPosition(370,16);
		world.addActor(key2);

		Monkey monkey = new Monkey();
		monkey.setPosition(842.2f,213.5f);
		world.addActor(monkey);

		Troll troll = new Troll();
		troll.setPosition(754.5f,178.5f);
		world.addActor(troll);

		Dizzy dizzy = new Dizzy();
		dizzy.setPosition(100,50);
		world.addActor(dizzy);
		world.centerOn(dizzy);

	}

	@Override
	public World getWorld()
	{
		return world;
	}
}
