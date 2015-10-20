var should=require('chai').should(),
    expect=require('chai').expect,
    assert=require('chai').assert,
    supertest=require('supertest'),
    api=supertest('http://frengly.com/?');

describe('APItest', function(){
    var res;
    it('Should return the translated data',function(result){
        api.get('src=fr&dest=en&text=Bonjour+monsieur&outformat=json&email=pradeepchaitu1992@gmail.com&password=9989807198').set('Accept','application/json').expect(200).end(function(error,resp){
            expect(resp.body.translation).to.equal('Good morning sir');
//            expect(resp.body.city.country).to.equal('GB');
//            expect(resp.body.cod).to.equal('200'); 
            result();             
        });
    });
})



//http://frengly.com/?src=fr&dest=en&text=Bonjour+monsieur&outformat=json&email=pradeepchaitu1992@gmail.com&password=9989807198