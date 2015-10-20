var should=require('chai').should(),
    expect=require('chai').expect,
    assert=require('chai').assert,
    supertest=require('supertest'),
    api=supertest('http://api.openweathermap.org/data/2.5/forecast/daily?');

describe('APItest', function(){
    var res;
    it('Should return the data for the particular location',function(result){
        api.get('q=london&mode=json&units=metric&cnt=7&appid=6592931011456bf7e824174e29720be8').set('Accept','application/json').expect(200).end(function(error,resp){
             expect(resp.body.city.name).to.equal('London');
             expect(resp.body.city.country).to.equal('GB');
            expect(resp.body.cod).to.equal('200'); 
            result();             
        });
    });
})
