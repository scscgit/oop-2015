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

package sk.tuke.yolkfolk;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.World;
import sk.tuke.gamelib2.WorldScreen;
import sk.tuke.yolkfolk.actors.ActorFactoryImpl;
import sk.tuke.yolkfolk.actors.characters.Daisy;
import sk.tuke.yolkfolk.actors.characters.Devil;
import sk.tuke.yolkfolk.actors.characters.Monkey;
import sk.tuke.yolkfolk.actors.characters.Troll;
import sk.tuke.yolkfolk.actors.items.Diamond;
import sk.tuke.yolkfolk.actors.objects.Elevator;
import sk.tuke.yolkfolk.actors.items.Lever;
import sk.tuke.yolkfolk.actors.player.players.dizzy.Dizzy;
import sk.tuke.yolkfolk.collectables.Apple;
import sk.tuke.yolkfolk.collectables.Key;

/**
 * Welcome to MagicLand! Let the hate flow...
 * <p/>
 * Created by Steve on 9.11.2015.
 */
public class MagicLand extends WorldScreen
{
	private NewWorldOrder world;

	@Override
	public void show()
	{
		//demo2();
		runDizzyGame();
	}

	protected void runDizzyGame()
	{
		//World exists.
		this.world = new NewWorldOrder();

		//Adding all objects, both living and dead, will be automatic for all next maps.
		this.world.setFactory(new ActorFactoryImpl());

		//Default map is started.
		this.world.setMap("map.tmx");

		//Music theme gets loaded.
		this.world.loadMusic(GameMusic.getThemePath());
	}

	@Deprecated
	protected void demo2()
	{
		this.world = new NewWorldOrder();
		this.world.setMap("cviko2.tmx");

		ActorFactoryImpl factory = new ActorFactoryImpl();
		Actor fire = factory.create("Fire");
		fire.setPosition(48, 80);
		this.world.addActor(fire);

		Daisy daisy = new Daisy();
		daisy.setPosition(200, 15);
		this.world.addActor(daisy);

		Apple apple = new Apple();
		apple.setPosition(150, 15);
		this.world.addActor(apple);

		Apple apple2 = new Apple();
		apple2.setPosition(170, 15);
		this.world.addActor(apple2);

		Diamond diamond = new Diamond("green");
		diamond.setPosition(20, 15);
		this.world.addActor(diamond);

		Diamond diamond2 = new Diamond("silver");
		diamond2.setPosition(60, 15);
		this.world.addActor(diamond2);

		Elevator elevator = new Elevator();
		elevator.setPosition(495, 16);
		elevator.setEnd(120f);
		this.world.addActor(elevator);

		Lever lever = new Lever(elevator);
		lever.setPosition(465, 16);
		this.world.addActor(lever);

		Lever lever2 = new Lever(elevator);
		lever2.setPosition(435, 16);
		this.world.addActor(lever2);

		Devil devil = new Devil();
		devil.setPosition(605, 145);
		this.world.addActor(devil);

		Key key = new Key();
		key.setPosition(400, 16);
		this.world.addActor(key);

		Key key2 = new Key();
		key2.setPosition(370, 16);
		this.world.addActor(key2);

		Monkey monkey = new Monkey();
		monkey.setPosition(842.2f, 213.5f);
		this.world.addActor(monkey);

		Troll troll = new Troll();
		troll.setPosition(754.5f, 178.5f);
		this.world.addActor(troll);

		Dizzy dizzy = new Dizzy();
		dizzy.setPosition(100, 50);
		this.world.addActor(dizzy);
	}

	@Override
	public World getWorld()
	{
		return this.world;
	}
}
