# Fight2Survive v1.1.4

Minecraft Plugin for 1.8.9

## To Do

### Fix

- Clean code : Ajout de commentaire, contexte
- A tester condition réelle ~~Equilibrer spawn des mobs~~
- ~~Replace par Bedrock quand block est cassé par joueur~~ + afficher cooldown
- Voir pour remplacer les loots des blocks dérivés ~~Pioche peux casser stone~~
- fix position spawn mob + sphere arround
- Empecher le drop de la boussole

### Ajout

- Random Event
- Options / Config de jeu au start
- Ajout d'un inventaire custom au start
- Disparition des mobs quand phase finale commence

### Idée

- Amélioration du starter kit au déblocage des salles
- Diversité des mobs spawn

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

9. Télécharger [la map pour le plugin](https://drive.google.com/file/d/12zyh25ZAXubfTHzGPpCUNI7qnb7qsV2M/view?usp=drive_link)
10. Décompiler le, puis mettre le dossier "Arene" dans le dossier du Serveur Minecraft
11. Redémarer le serveur (en exécutant `run.bat`).

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
