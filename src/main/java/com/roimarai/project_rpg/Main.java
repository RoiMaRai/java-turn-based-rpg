package com.roimarai.project_rpg;

import com.roimarai.project_rpg.entity.Entity;
import com.roimarai.project_rpg.entity.Player;
import com.roimarai.project_rpg.entity.enemy.Slime;
import com.roimarai.project_rpg.weapon.onehanded.Sword;

import java.util.Scanner;

public class Main 
{
    public static void main( String[] args )
    {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter name: ");
        String name = input.nextLine();
        Player player = new Player(name);
        player.equip(Sword.armingSword());
        player.printData();

        Entity slime = new Slime(1);

        input.close();
    }
}
