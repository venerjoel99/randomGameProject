/**
 * The deck class that creates, shuffles, and prints cards
 */
function Deck(){
	//The array of card objects
	this.cards = [];
	//Ranks and suits of the cards
	this.ranks = [2, 3, 4, 5, 6, 7, 8, 9, 10, 'jack', 'queen', 'king', 'ace'];
	this.suits = ['spades', 'clubs', 'diamonds', 'hearts'];
	//Create the 52 cards each with each own rank and suit
	for (var i = 0; i < this.suits.length; i++){
		//Each card's point value depends on its rank
		for (var j = 0; j < this.ranks.length; j++){
			var point = null;
			switch(this.ranks[j]){
				case 'jack':
					point = 1;
					break;
				case 'queen':
					point = 2;
					break;
				case 'king':
					point = 3;
					break;
				case 'ace':
					point = 4;
					break;
				default:
					point = 0;
					break;
			}
			//Adds a card to the end of the card array
			this.cards.push({
				rank : this.ranks[j],
				suit : this.suits[i],
				value : point});
		}
	}
	this.size = this.cards.length;
	this.isEmpty = function(){
		return (this.cards==null || this.cards.length<=0);
	}
	//Function that prints the card info on the console
	this.printCards = function(){
		for (var k = 0; k < this.cards.length; k++){
			console.log(this.cards[k].toString());
		}
	}
	this.deal = function(){
		var index = this.size - 1;
		if (!this.isEmpty()){
			this.size--;
			return this.cards[index];
		}
		else return null;
	}
	this.shuffle = function(){
		for (var i = this.cards.length-1; i>0; i--){
			var r = Math.floor(Math.random()*(i+1));
			if (r!=i){
				var temp = this.cards[i];
				this.cards[i] = this.cards[r];
				this.cards[r] = temp;
			}
		}
	}
	this.match = function(card1, card2){
		if (card1==null || card2==null){
			return false;
		}
		return (card1.rank==card2.rank &&
			card1.suit==card2.suit &&
			card1.value==card2.value);
	}
	this.cardStr = function(card){
		return card.rank + " of " + card.suit + "(" + card.value + ")";
	}
	this.toString = function(){
		var rtn = "size =" + this.size + "\n";
		for (var i = 0; i < this.cards.length; i++){
			rtn += (this.cards[i].toString()+"\n");
		}
		return rtn;
	}
	this.shuffle();
}

module.exports = Deck;