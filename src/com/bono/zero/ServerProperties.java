package com.bono.zero;

/**
 * Created by hendriknieuwenhuis on 24/07/15.
 */
public class ServerProperties {

    /**
     * Connection commands:
     */
    public static final String CLEAR_ERROR     = "clearerror";
    public static final String CLOSE           = "close";
    public static final String STATUS          = "status";
    public static final String STATISTICS      = "stats";
    public static final String START_COM_LIST  = "command_list_ok_begin";
    public static final String END_COM_LIST    = "command_list_end";
    public static final String PASSWORD        = "password";
    public static final String PING            = "ping";

    /**
     * Responses:
     */
    public static final String OK              = "OK";
    public static final String ERROR           = "ACK";

    /**
     * Admin commands:
     */
    public static final String DISABLE_OUT     = "disableoutput";
    public static final String ENABLE_OUT      = "enableoutput";
    public static final	String KILL            = "kill";
    public static final String OUTPUTS         = "outputs";
    public static final String REFRESH         = "update";
    public static final String COMMANDS        = "commands";
    public static final String NOT_COMMANDS    = "notcommands";

    /**
     * Database commands:
     */
    public static final String FIND            = "find";
    public static final String LIST_TAG        = "list";
    public static final	String LIST_ALL        = "listall";
    public static final String LIST_ALL_INFO   = "listallinfo";
    public static final String LIST_INFO       = "lsinfo";
    public static final String SEARCH          = "search";
    public static final String LIST_SONGS      = "listplaylist";

    /**
     * Playback commands:
     */
    public static final String CROSSFADE       = "crossfade";
    public static final String CURRENTSONG     = "currentsong";
    public static final String CONSUME         = "consume";
    public static final String NEXT            = "next";
    public static final String PAUSE           = "pause";
    public static final String PLAY            = "play";
    public static final String PLAY_ID         = "playid";
    public static final String PREVIOUS        = "previous";
    public static final String REPEAT          = "repeat";
    public static final String RANDOM          = "random";
    public static final String SEEK            = "seek";
    public static final String SEEK_ID         = "seekid";
    public static final String SINGLE          = "single";
    public static final String STOP            = "stop";
    public static final String VOLUME          = "setvol";

    /**
     * Playlist commands:
     */
    public static final String ADD             = "add";
    public static final String CLEAR           = "clear";
    public static final String CURRSONG        = "currentsong";
    public static final String DELETE          = "rm";
    public static final String CHANGES         = "plchanges";
    public static final String LIST_ID         = "playlistid";
    public static final String LIST            = "playlistinfo";
    public static final String LOAD            = "load";
    public static final String MOVE            = "move";
    public static final String MOVE_ID         = "moveid";
    public static final String REMOVE          = "delete";
    public static final String REMOVE_ID       = "deleteid";
    public static final String SAVE            = "save";
    public static final String SHUFFLE         = "shuffle";
    public static final String SWAP            = "swap";
    public static final String SWAP_ID         = "swapid";

    public static final String ADD_PLAYLIST    = "playlistadd";
}
