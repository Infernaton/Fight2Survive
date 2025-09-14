# Fight2Survive v1.3

Minecraft Plugin for 1.8.9

## To Do

#### Pour 1.3

- Faire clé virtuelle :
    - Pour débloquer une porte -> avoir les ressources nécessaire dans son inventaire
    - Faire écriteau indiquant les ingrédients pour ouvrir la porte
    - Supprimer PNJ devenus useless (voir pour les transformer en vrai trader)
- Améliorer l'arriver en Phase Finale :
    - ~~Message en full screen~~
    - ~~Bruit d'apparition du dragon~~
    - Timer avant l'activation de cette phase
- Afficher l'équipe gagnante de la partie
- ~~Voir pour que les mobs prennent des dégats instantanés lorsque le jour se lève~~

### Fix

-   Clean code : Ajout de commentaire, contexte
-   ~~Equilibrer spawn des mobs~~ A tester condition réelle
-   ~~Pioche peux casser stone~~ Voir pour remplacer les loots des blocks dérivés
-   Durabilité des outils bizarre
-   ~~Position de la salle de l'or coince la progression (ne peux pas casser)~~ Ajout de minerai de fer dans la carrière de charbon
-   ~~Indiquer où vont les portes avant de les ouvrir~~ Manque la porte finale
-   Si quelqu'un meurt avec une clé dans la lave -> bloqué
    -   fix possible -> faire qu'elle reste dans l'inventaire du joueur qui l'achete \
        -> faire des cléfs virtuelle par équipe
-   Voir pour casser le différents bois ?

### Ajout

-   Random Event
-   Améliorer l'entrée de la phase final (message en pleine ecran + bruit)
-   Options / Config de jeu au start
    -   Random Event
    -   Auto smelt
    -   Only day / only night
    -   Craft/Items custom prédéfinis :
        -   Oeuf de golem
        -   Pouvoir se téléporter dans le camps ennemis pendant un certain temps (~30s) ?
-   Ajout d'un inventaire custom au start
-   Disparition des mobs quand phase finale commence ?
-   Ajouter du gravier + pelle / voir pour les plumes
-   Voir pour ajouter des barrieres pour la fin du jeu
-   Afficher l'équipe gagnante

### Idée

-   Supprimer pnj après trade
-   Activé mode UHC uniquement en phase final
-   Voir pour faire mode peaceful quand le jour se lève
-   Disparition des mobs quand phase finale commence ?
-   Amélioration du starter kit au déblocage des salles
-   Diversité des mobs spawn
-   Faire un random de team
-   Faire une salle pour les potions
-   Faire un spleef d'attente

## Setup Vs Code

### Initialisation

1. Télécharge le projet
2. Installe l'extension [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) pour VS Code
3. Télécharger la [librairie Bukkit](https://drive.google.com/file/d/18oXDvNw4vY8TLZLlGhJXhrQaC6Yr_xYd/view?usp=drive_link) et la stocker à un endroit safe
4. Dans l'explorer vs code, Java Projects > Referenced Librairies, cliquer sur le '+' pour ajouter la librairie bukkit.
5. Fin.

### Serveur Minecraft Local

#### Installation "Copier Coller" - Si le lien google drive fonctionne toujours

1. Télécharger [ce zip](https://drive.google.com/file/d/1wokNxDip6mNsxHQbjXGU0lp5UpJW7k-1/view?usp=drive_link) via Google Drive
2. Décompiler-le
3. Séléctionner le dossier "1.8 Minecraft Server". Il contient le serveur minecraft prêt à être utilisé.
4. Le déplacer où bon vous semble.
5. Pour démarer le serveur, double cliquer sur le fichier `run.bat`

Note: La map minecraft et le plugin ne seront peut être pas à jour. Si c'est le cas suivez à partir de `9.` de l'installation manuelle.

#### Installation Manuelle

1. Créer un répertoire "Minecraft server" sur le bureau (il peux être placer où cela arrange et renommé comme souhaité)
2. A l'intérieur, glisser la [librairie Bukkit](https://drive.google.com/file/d/18oXDvNw4vY8TLZLlGhJXhrQaC6Yr_xYd/view?usp=drive_link) et renommer la en `spigot-1.8.8.jar`
3. Créer un fichier `run.bat` contenant

```bat
java -Xmx1024M -Xms1024M -jar spigot-1.8.8.jar nogui
PAUSE
```

Note: `Xmx1024M` représente la RAM maximum que va utiliser le serveur, cette valeur peux être modifier, mais n'est pas nécessaire, les ressources demandé par la map + plugin ne sont pas très importante.

4. Exécuter `run.bat`
5. Des fichiers ont été créer, dans le fichier `eula.txt` remplacer "FALSE" par "TRUE"
6. Re-exécuter `run.bat` pour créer les dernires fichiers
7. Dans la console, écriver `stop` pour arrêter le serveur
8. Dans `server.properties`, modifier en priorité

```txt
spawn-protection=5
force-gamemode=false
allow-nether=false
gamemode=2
difficulty=1
enable-command-block=true
level-name=Arene
```

9. Dans ce repository, dans `/minecraft-map`, copier/coller le dossier "Arene" dans le dossier du Serveur Minecraft
10. Redémarer le serveur (en exécutant `run.bat`).

A ce stade il ne manque plus qu'à compiler le plugin et l'ajouter au dossier `/plugin` pour que le serveur soit opérationnel.

### Pour compiler le projet

1. Terminal > Run Build Task
2. Terminal > Run Task > java (buildartifact) > configure task (la roue dentée)
3. Dans le json, modifie en y ajoutant le chemin vers le server minecraft local :

```json
"targetPath": "path/to/minecraft server/plugins/${workspaceFolderBasename}.jar",
```

4. Egalement :

```json
"elements": [
    "${workspaceFolder}/config.yml",
    "${workspaceFolder}/plugin.yml",
    "${compileOutput}",
    "${dependencies}"
],
```

5. Compile le projet puis une fois finis, redémarer le server local ou faire `/reload` en jeu via la console ou un joueur op.
