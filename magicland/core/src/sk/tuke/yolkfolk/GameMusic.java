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
	//Konstantne cesty ku jednotlivym hudbam a zvukovym efektom
	private static final String PATH_VAULT = "music/vault.mp3";
	private static final String PATH_CORE = "music/core.mp3";
	private static final String PATH_SECRET = "music/secret.mp3";
	private static final String PATH_DOGSONG = "music/dogsong.mp3";
	private static final String PATH_GAME_OVER = "music/game_over.mp3";
	private static final String PATH_FINISH_HIM = "music/finish_him.mp3";

	//Songy pre background theme
	public static String getThemeInPath()
	{
		return GameMusic.PATH_VAULT;
	}
	public static String getThemeOutPath()
	{
		return GameMusic.PATH_CORE;
	}
	public static String getSecretPath()
	{
		return GameMusic.PATH_SECRET;
	}
	public static String getWinPath()
	{
		return GameMusic.PATH_DOGSONG;
	}

	public static Music playGameOver()
	{
		Music music = new Music(GameMusic.PATH_GAME_OVER);
		music.play();
		music.setLooping(false);
		return music;
	}

	public static Music playFinishHim()
	{
		Music music = new Music(GameMusic.PATH_FINISH_HIM);
		music.play();
		music.setLooping(false);
		return music;
	}
}
