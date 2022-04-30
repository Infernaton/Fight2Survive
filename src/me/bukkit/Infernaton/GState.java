package me.bukkit.Infernaton;

/**
 * WAITING -> When there no game running on the server, the default state
 * STARTING -> The exact state between an actual game and the default, to tell when the game is about to start
 * PLAYING -> The actual game
 * FINISH -> Like STARTING, a state between the game and default state, to tell when the game is finish
 */
public enum GState {
    WAITING, STARTING, PLAYING, FINISH;
}

