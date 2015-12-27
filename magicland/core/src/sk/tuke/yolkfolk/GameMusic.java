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

import sk.tuke.gamelib2.Music;

/**
 * Jednotne miesto, kde sa nachadzaju vsetky hudby pouzivane v hre.
 * <p/>
 * Created by Steve on 27.12.2015.
 */
public class GameMusic
{
	//Konstantne cesty ku jednotlivym hudbam
	private static final String theme = "vault.mp3";
	private static final String game_over = "game_over.mp3";
	private static final String secret = "secret.mp3";
	private static final String finish_him = "finish_him.mp3";

	//Vyberie jednu z dostupnych songov pre background theme
	public static String getThemePath()
	{
		return GameMusic.theme;
	}

	public static String getSecretPath()
	{
		return GameMusic.secret;
	}

	public static Music playGameOver()
	{
		Music music = new Music(GameMusic.game_over);
		music.play();
		music.setLooping(false);
		return music;
	}

	public static Music playFinishHim()
	{
		Music music = new Music(GameMusic.finish_him);
		music.play();
		music.setLooping(false);
		return music;
	}
}
