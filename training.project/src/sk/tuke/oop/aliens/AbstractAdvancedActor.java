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

import java.util.ArrayList;
import java.util.List;

/**
 * Vyznam AdvancedActora:
 *  Pridava vseobecne premenne a funkcie v ramci projektu, ktore by sa nachadzali v kazdej podtriede AbstractActor, takze tuto triedu nahradza.
 * 
 * Zoznam doplnkov:
 * 1. Pridana podpora cachingu Animacii
 * 
 * @author Steve
 */
public abstract class AbstractAdvancedActor extends sk.tuke.oop.aliens.actor.AbstractActor
{
    /************************************\
    |* Week 3, AdvancedActor by Steve
    \************************************/
    
    private List<Animacia> animacie; //Pole animacii
    private Animacia currentAnimacia; //Aktualne zapnuta animacia
    private int animacieIndex; //Index urcujuci nasledujucu animaciu na vznik, tj. pocet existujucich animacii
    
    public AbstractAdvancedActor()
    {
        currentAnimacia = new Animacia(0,"",0,0,0); //Defaultna null animacia, ktorou sa ziadna skutocna animacia nesmie rovnat
        animacie = new ArrayList<>(); //Inicializacia pola animacii
	animacieIndex = 0; //Animacie zacnu od cisla indexu 0, po inicializacii existuje 0 animacii
    }
    
    //Prida novu animaciu, pripadne vrati index existujucej, ktora je semanticky ekvivalentna novej
    protected int addAnimation(String png, int x, int y, int speed, boolean pingpong, boolean looping)
    {
	Animacia novaAnimacia = new Animacia(this.animacieIndex++, png, x, y, speed, pingpong, looping);
        for(Animacia i: animacie)
        {
            //ak sa i-ta animacia semanticky rovna novej animacii, tak uz existuje
            if(i.equalsAnimacia(novaAnimacia))
            {
                //vratim jej index a usetrim miesto v pamati tym, ze ju nemusim znovu vyrabat
                return i.getIndex();
            }
        }
        //Ak nebola najdena, tak ju pridam do pola
        animacie.add(novaAnimacia);
	//Vraciam novy index
	return novaAnimacia.getIndex();
    }
    
    //Implicitne parametre
    protected int addAnimation(String png, int x, int y, int speed)
    {
	return addAnimation(png, x, y, speed, true, true);
    }
    
    //Nastavi novu rychlost aktualnej animacie, bez jej aktualizacie v poli animacii. Nezmeni premenne aktualnej animacie.
    protected void setAnimationSpeed(int speed)
    {
        currentAnimacia.getNormalAnimation().setDuration(speed);
    }
    
    //Pokial bola zmenena rychlost animacie, moze byt potrebne ziskat naspat defaultnu rychlost animacie.
    protected int getAnimationSpeed()
    {
        return currentAnimacia.getSpeed();
    }
    
    //Nastavi opakovanie animacie, bez jej aktualizacie v poli animacii. Nezmeni premenne aktualnej animacie.
    protected void setAnimationLooping(boolean on)
    {
        currentAnimacia.getNormalAnimation().setLooping(on);
    }
    
    //Prepne aktualnu animaciu na novu, ktora uz existuje v poli animacie, pricom ju definuje nazvom png.
    //Tato funkcia funguje iba na vyhladavanie generickych animacii. V pripade odlisnych parametrov je potrebne spustat ich pomocou indexu.
    protected void updateAnimation(String png)
    {
        //Ak nastala zmena animacie
        if(!this.currentAnimacia.getPng().equals(png))
        {
            //Najdem danu animaciu v zozname animacii
            for(Animacia i: animacie)
            {
                //ak ma i-ta animacia meno png suboru = png
                if(i.getPng().equals(png))
                {
                    //tak jej animaciu spustim a nastavim ju na novu aktualnu animaciu
                    runAnimacia(i);
                    return;
                }
            }
	    System.out.println("Textura "+png+" nebola najdena.");
	    //Ak nebola najdena, tak... asi nic no. Mohol by som hodit aj chybu, alebo dat obrazok na chybovy.
        }
    }
    
    //Prepne aktualnu animaciu na novu, na zaklade jej indexu v ramci aktualneho objektu/triedy
    protected void updateAnimation(int index)
    {
	//Ak nastala zmena animacie
        if(this.currentAnimacia.getIndex() != index)
        {
            //Najdem danu animaciu v zozname animacii
            for(Animacia i: animacie)
            {
                //ak ma i-ta animacia meno png suboru = png
                if(i.getIndex() == index)
                {
                    //tak jej animaciu spustim a nastavim ju na novu aktualnu animaciu
                    runAnimacia(i);
                    return;
                }
            }
	    System.out.println("Textura s indexom "+index+" nebola najdena.");
	    //Ak nebola najdena, tak... asi nic no. Mohol by som hodit aj chybu, alebo dat obrazok na chybovy.
        }
    }
    
    private void runAnimacia(Animacia a)
    {
	setAnimation(a.getNormalAnimation());
	this.currentAnimacia = a;
    }
    
    public final int getAnimacieIndex()
    {
	return this.animacieIndex;
    }
    
    public final void increaseAnimacieIndex()
    {
	this.animacieIndex++;
    }
    
    /* Obsolete funkcie, ktore nemaju uplatnenie
    protected Animacia findAnimationIndex(int index)
    {
	for(Animacia a: animacie)
	{
	    if(a.index == index)
	    {
		return a;
	    }
	}
	return null;
    }
    
    protected void runAnimationIndex(int index)
    {
	Animacia a = findAnimationIndex(index);
	setAnimation(a.normalAnimation);
	currentAnimacia = a;
    }
    */
}
