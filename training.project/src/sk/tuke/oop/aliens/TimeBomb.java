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
 * Kazda bomba moze vybuchnut, ale niektora iba raz.
 * 
 * @author Steve
 */
public class TimeBomb extends AbstractAdvancedActor
{
    /************************************\
    |* Week 4 Doplnkove ulohy
    \************************************/
    
    //Experimentalne namerana hodnota trvania priemerneho vybuchu vybusniny od naseho dodavatela je priblizne 42 casovych jednotiek.
    //Vyplyva z toho, ze zmysel zivota je vybuch bomby? Smutne.
    public static final int EXPLODING_TIMER = 42;
    
    private int time;
    private boolean armed;
    private boolean exploding;
    private int explodingTimer;
    private boolean exploded; //Mozno po odstraneni actora prestane actor existovat. Vtedy by tato premenna bola zbytocna.
    
    public TimeBomb(int time)
    {
	//Vstupy
	setTime(time);
	
	//Inicializacia
	this.armed=false;
	this.exploding=false;
	this.exploded=false;
	
	//Animacie
	addAnimation("bomb", 16, 16, 100);
	addAnimation("bomb_activated", 16, 16, 100);
	addAnimation("small_explosion", 16, 16, 100, false, false);
	updateAnimation();
    }
    
    private void updateAnimation()
    {
	if(isArmed())
	{
	    if(isExploding())
	    {
		updateAnimation("small_explosion");
	    }
	    else
	    {
		updateAnimation("bomb_activated");
	    }
	}
	else
	{
	    updateAnimation("bomb");
	}
    }
    
    private void setTime(int time)
    {
	this.time = time;
    }
    
    private int getTime()
    {
	return this.time;
    }
    
    public void activate()
    {
	this.armed = true;
	updateAnimation();
    }
    
    public boolean isArmed()
    {
	return this.armed;
    }
    
    private void explode()
    {
	this.exploding=true;
	this.explodingTimer=EXPLODING_TIMER;
	updateAnimation();
    }
    
    public boolean isExploding()
    {
	return this.exploding;
    }
    
    @Override
    public void act()
    {
	if(!this.exploded)
	{
	    //Znizenie casovaca
	    if(getTime()>0 && isArmed())
	    {
		setTime(getTime() - 1);
	    }

	    //Kontrola, ci nema nastat vybuch
	    if(!isExploding() && isArmed() && getTime() <= 0)
	    {
		explode();
	    }

	    //Ak nastal vybuch, po skonceni treba bombu zmazat
	    if(isExploding())
	    {
		if(this.explodingTimer <= 0)
		{
		    this.exploded=true;
		    getWorld().removeActor(this);
		}
		else
		{
		    this.explodingTimer--;
		}
	    }
	}
    }
}
