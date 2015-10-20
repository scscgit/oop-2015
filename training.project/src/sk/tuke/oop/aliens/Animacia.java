/* * * * * * * * * * * * * * * *
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package sk.tuke.oop.aliens;

/**
 * normalAnimation v2.0
 * 
 * @author Steve
 */
//Objekt na ulozenie animacie
public class Animacia
{
    private sk.tuke.oop.framework.Animation normalAnimation;
    private int index;
    private String png;
    private int x;
    private int y;
    private int speed;
    private boolean pingpong;
    private boolean looping;

    public Animacia(int index, String png, int x, int y, int speed, boolean pingpong, boolean looping)
    {
	//Pomocka pre pripad, ze nabuduce nebudem vediet preco to crashlo
	if (pingpong && !looping)
	{
	    System.out.println("Warning: " + png + "has pingpong without looping, expecting crash!");
	}
	this.normalAnimation = new sk.tuke.oop.framework.Animation("resources/images/" + png + ".png", x, y, speed);
	normalAnimation.setPingPong(pingpong);
	normalAnimation.setLooping(looping);
	this.index = index;
	this.png = png;
	this.x = x;
	this.y = y;
	this.speed = speed;
	this.pingpong = pingpong;
	this.looping = looping;
    }

    //Implicitne parametre konstruktora
    public Animacia(int index, String png, int x, int y, int speed)
    {
	this(index, png, x, y, speed, true, true);
    }

    //Semanticka rovnost dvoch animacii
    //V tomto pripade je definovana rovnostou vsetkych clenov. (Trivia: stara verzia to definovala na zaklade rovnakeho stringu png textury)
    public boolean equalsAnimacia(Animacia a)
    {
	return (this.png.equals(a.png) && this.x == a.x && this.y == a.y && this.speed == a.speed && this.pingpong == a.pingpong && this.looping == looping);
    }
    
    //Ziskavacie funkcie na parametre
    public sk.tuke.oop.framework.Animation getNormalAnimation()
    {
	return this.normalAnimation;
    }
    
    public int getIndex()
    {
	return this.index;
    }
    
    public String getPng()
    {
	return this.png;
    }
    
    public int getX()
    {
	return this.x;
    }
    
    public int getY()
    {
	return this.y;
    }
    
    public int getSpeed()
    {
	return this.speed;
    }
    
    public boolean getPingpong()
    {
	return this.pingpong;
    }
    
    public boolean getLooping()
    {
	return this.looping;
    }

    @Override
    public String toString()
    {
	return this.png;
    }
}
