function Ratscrew(players){
	var Deck = require("C:/Users/84ven/Desktop/JavaScript/deck.js");
	this.deck = new Deck();
	this.cardPile = [];
	this.playerPiles = [];
	this.dealing = true;
	this.players = players;
	this.currentPlayer = 0;
	this.faceCardPlayer = -1;
	this.numberToDraw = 0;
	for (var i = 0; i < this.players; i++){
		this.playerPiles.push([]);
	}
	this.switchTurn = function(){
		if (this.currentPlayer==this.players-1) {
			this.currentPlayer = 0;
		}
		else {
			this.currentPlayer++;
		}
		if (!this.dealing && this.playerPiles[this.currentPlayer].length==0){
			this.switchTurn();
		}
	}
	this.newGame = function(){
		this.dealing = true;
		this.deck.shuffle();
		this.currentPlayer=0;
		for(var i = 0; i < this.playerPiles.length; i++){
			this.playerPiles[i] = [];
		}
		for (var j = 0; j < this.deck.cards.length; j++){
			this.playerPiles[this.currentPlayer].push(this.deck.deal());
			this.switchTurn();
		}
		this.currentPlayer=0;
		this.dealing = false;
	}
	this.pair = function(){
		if (this.cardPile.length <= 1) return false;
		card1 = this.cardPile[this.cardPile.length-1];
		card2 = this.cardPile[this.cardPile.length-2];
		if (card1!=null && card2!=null)
			{
				return card1.rank==card2.rank;
			}
		return false;
	}
	this.sandwich = function(){
		if (this.cardPile.length <= 2) return false;
		card1 = this.cardPile[this.cardPile.length-1];
		card3 = this.cardPile[this.cardPile.length-3];
		if (card1!=null && card3!=null){
			return card1.rank==card3.rank;
		}
		return false;
	}
	this.faceCard = function(){
		if (this.cardPile.length>0){
			return (this.cardPile[this.cardPile.length-1].value>0);
		}
		return false;
	}
	this.claimable = function(){
		return (this.faceCardPlayer!=-1 && this.numberToDraw==0);
	}
	this.legalClaim = function(claimer){
		if(this.claimable() && this.faceCardPlayer==claimer) return true;
		if(this.pair() || this.sandwich()) return true;
		return false;
	}
	this.gameWinner = function(){
		var winner = -1;
		for(var i = 0; i < this.players; i++){
			var cards = 0;
			for (var j = 0; j < this.players; j++){
				if(i!=j) cards += this.playerPiles[j].length;
			}
			if (cards==0){
				winner = i;
			}
		}
		if(this.cardPile.length > 0 &&
			this.cardPile[this.cardPile.length-1].value>0 && 
			this.faceCardPlayer!=winner){
				return -1;
			}
		return winner;
	}
	this.gameOver = function(){
		if(!this.pair() && !this.sandwich() && !this.claimable()){
			if(this.gameWinner()!=-1) return true;
			if(this.cardPile.length==this.deck.cards.length) return true;
		}
		return false;
	}
	this.claim = function(currentClaimer){
		if (!this.gameOver() && this.legalClaim(currentClaimer)){
			var cardPileSize = this.cardPile.length;
			while(this.cardPile.length>0){
				this.playerPiles[currentClaimer].
				unshift(this.cardPile.shift());
			}
			this.currentPlayer = currentClaimer;
			this.faceCardPlayer = -1;
			this.numberToDraw = 0;
			return true;
		}
		if (this.playerPiles[currentClaimer].length>0){
			this.cardPile.unshift(this.playerPiles[currentClaimer].shift());
		}
		return false;
	}
	this.draw = function(currentDrawer){
		if (!this.gameOver() && this.currentPlayer==currentDrawer
		&& !this.claimable()){
			var size = this.playerPiles[this.currentPlayer].length;
			if (size>0){
				this.cardPile.push(this.playerPiles[this.currentPlayer].pop());
				if(this.faceCard()){
					this.numberToDraw = this.cardPile[this.cardPile.length-1].value;
					this.faceCardPlayer = this.currentPlayer;
					this.switchTurn();
				}
				else if(this.numberToDraw>0){
					this.numberToDraw--;
				}
				else{
					this.switchTurn();
				}
				return true;
			}
			return false;
		}
		return false;
	}
	this.newGame();
}	

module.exports = Ratscrew;