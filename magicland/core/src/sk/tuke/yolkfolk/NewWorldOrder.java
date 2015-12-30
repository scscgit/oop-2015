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

package sk.tuke.yolkfolk;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Music;
import sk.tuke.gamelib2.World;

/**
 * You think you have freedom of choice. You don't. Not in this World.
 * You will get a free music by using this world, though.
 * Novus ordo seclorum.
 * <p/>
 * Created by Steve on 26.12.2015.
 */
public class NewWorldOrder extends World
{
	//Constants
	//Konstanta urcujuca, ako daleko sa predmet ma nachadzat, aby ho nebolo vidno
	public static final int OUTSIDE_WORLD = 800;

	//Variables
	private Music music;
	private String musicPath;

	{
		this.music = null;
		this.musicPath = "";
	}

	//Nacita hlavnu hudbu pre celu hru. Vzdy moze byt naraz spustena iba jedna.
	public void loadMusic(String path)
	{
		if (!musicPath.equals(path))
		{
			stopMusic();
			this.music = new Music(path);
			this.musicPath = path;
			this.music.play();
			this.music.setLooping(true);
		}
	}

	//Receive the current instance of illusion of choice of your own music
	public Music getMusic()
	{
		return this.music;
	}

	//Stop the illusion of music
	public void stopMusic()
	{
		if (getMusic() != null)
		{
			getMusic().stop();
		}
	}

	//Teleports Actor outside the World so wrong collisions won't happen, used as a hotfix
	public static void teleportOutside(Actor actor)
	{
		actor.setPosition(-NewWorldOrder.OUTSIDE_WORLD, -NewWorldOrder.OUTSIDE_WORLD);
	}
}
