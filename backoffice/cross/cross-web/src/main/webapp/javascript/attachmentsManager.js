class AttachmentsManager {
	
  	constructor(maxNumber, maxSize, errorMessage) {
    	this.attachments = {}
    	this.thereIsLimit = !!maxNumber || !!maxSize
    	if(this.thereIsLimit){
			this.maxNumber = 1*maxNumber;
    		this.maxSize = 1*maxSize;
		}
		this.validSize = true;
		this.validNumber = true;
		this.errorMessage = errorMessage;
		this.currentNumber = 0;
		this.currentSize = 0;
  	}
  	
	setAttachment(id, size, toAdd,showError){
		if(this.thereIsLimit){
			if(toAdd)
				this.attachments[id] = size
			else
				delete this.attachments[id]
			
			this.validSize = true;
			this.validNumber = true;
			this.currentNumber = Object.keys(this.attachments).length
			if(!!maxNumber && this.currentNumber > this.maxNumber){
				this.validNumber = false;
				if(showError)
					this.errorMessage("N")
			}
			else{
				this.currentSize = 0;
				for (const [_, value] of Object.entries(this.attachments)) {
  					this.currentSize += (value*1);
				}
				if(!!maxSize && this.currentSize > this.maxSize){
					this.validSize = false;
					if(showError)
						this.errorMessage("S")
				}
			}
		}
  	}

  	isValid(){
		  return !this.thereIsLimit || (this.validSize && this.validNumber);
	}
	
	printErrorMessage(){
		this.errorMessage(this.validSize?"N":"S")
	}
}
