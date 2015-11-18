describe("user", function (userFactory) {
    beforeEach(module('user.factory'));

    var userFactory, rest, cookies, hello;
    beforeEach(function () {
        angular.mock.inject(function ($injector) {
            rest = $injector.get('rest');
            userFactory = $injector.get('userFactory');
            cookies = $injector.get('$cookies');
            hello = $injector.get('hello');
        })
    });

    describe('asset user factory exists', function () {
        it("should return a string", inject(function () {
            // httpBackend.expectGET('/Home/Customers').respond(['david', 'James', 'Sam']);
            /*service.getString(function (result) {
                expect(result).toEqual(["david", "James", "Sam"]);
            });
            httpBackend.flush();*/
        }))
    })
});