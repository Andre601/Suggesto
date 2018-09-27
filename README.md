# Supporto
Supporto is a simple Ticket-bot, with a small twist.  
Instead of you having to type something like `/new <topic>` to create a ticket, Supporto simply lets you set a channel as a ticket-channel, where people can just type a message and the bot will make a new ticket.  
The creator, people with the permission `manage server` or a set staff-role can then close the ticket, by simply clicking on the reaction of the first message and confirming the close.

Every participient in the ticket will receive a transcript of the chat in a DM.

##NOTES
People with `manage server` permission or a registered staff-role assigned **Can NOT** create a ticket like normal.  
This was made to let people have a way, to make a message in their ticket-channel (e.g. for information).  
If you still want to create a ticket, add a `-test` to the message.

## Commands

### Bot
The default prefix for commands is `t_`.  
To know, what prefix is used in a guild, simply mention the bot.

#### Help
**Aliases**:
- Commands
- Command

Shows you all available commands.  
You can provide a command after `Help` (f.e. `t_help settings`) to get info about the command.

#### Invite
**Aliases**:
- link
- links

Gives you the links, to invite the bot, join the guild, or to this GitHub-repository here.

#### Stats
**Aliases**:
- stat
- statistics

Gives you some statistics about the bot.

### Guild
This commands are for either getting some guild-info, or for changing stuff.

#### Guild
**Alias**:
- server

Shows general info about the guild.

#### Settings
**Alias**:
- options

Lets you change different settings of the bot.  
This command requires you to have the `manage server` permission!

**Available options**:  

| option   | syntax                             | Description                                                 |  
| -------- | ---------------------------------- | ----------------------------------------------------------- |  
| prefix   | `prefix <set prefix\|reset>`       | Sets or resets the prefix for the guild.                    |  
| channel  | `channel <set #channel\|reset>`    | Sets or resets the channel for creating tickets.            |  
| category | `category <set categoryID\|reset>` | Sets or resets the category, where tickets are created.     |  
| role     | `role <set roleID\|reset>`         | Sets or resets a role that people could close tickets with. |  
