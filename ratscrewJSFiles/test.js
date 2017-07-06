/**
 * The class for each individual card object
 */
var Game = require("C:/Users/84ven/Desktop/JavaScript/ratscrew.js");

exports.game = new Game(4); 

exports.play = function(){	
	if (this.game.claimable()){
		person = this.game.faceCardPlayer;
		this.game.claim(this.game.faceCardPlayer);
		console.log("Claimed by " + person);
	}
	else if (this.game.pair() || this.game.sandwich()){
		rand = Math.floor(Math.random()*this.game.players);
		console.log("Claimed by " + rand);
		this.game.claim(rand);
	}
	else {
		if (this.game.playerPiles[this.game.currentPlayer].length<=0){
			this.game.switchTurn();
			console.log("SKIPPED");
		}
		else{
			console.log("Drawer: " + this.game.currentPlayer);
			this.game.draw(this.game.currentPlayer);
			console.log(this.game.deck.cardStr(this.game.cardPile[this.game.cardPile.length-1]));
		}
	}
}
	