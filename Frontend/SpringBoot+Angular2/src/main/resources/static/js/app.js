var mainApp = angular.module('examApp', [ 'ngRoute', 'AppCtrl' ]);

mainApp.controller('TimerController', function($scope, timerService) {
	$scope.timeData = {
			TimeRemain : ''
	}
	$scope.$watch(function() {
		return timerService.timing;
	}, function(data) {
		$scope.timeData.TimeRemain = data.timeRem;
	}, true);
});
mainApp.config(function($routeProvider) {
	$routeProvider
		.when('/question', {
			templateUrl : '/views/question.html',
			controller : 'QuestionNumberController as quest'
		})
		.when('/endexam', {
			templateUrl : '/views/endexam.html',
			controller : 'EndExamController as endexam'
		})
		.otherwise({
			templateUrl : '/views/login.html',
			controller : 'LoginController as logon'
		});
});
//
mainApp.factory('timerService', function($interval) {
	var clock = null;
	var timing = {
			timeRem : ''
	}
	var timeservice = {
		startClock : function(fn) {
			if (clock === null) {
				clock = $interval(fn, 2000);
			}
		},
		stopClock : function() {
			if (clock !== null) {
				$interval.cancel(clock);
				clock = null;
			}
		}
	};
	return {
		timeservice : timeservice,
		timing : timing
	};
});
mainApp.service('examService', function($http, $interval) {
	var globalVar = {
		userName : '',
		QuestionNo : '0',
		totalQuestions : '0',
		authenticate : true,
		timerData : [],
		beginTime : false,
		PrevQuestionNo : '0',
		Answers : [{"id":"Y","isChecked":"false"}]
	};
	var setAnswers = function(newObj) {
		globalVar.Answers = newObj;
	};
	var getAnswers = function() {
		return globalVar.Answers;
	};
	var setPrevQuestionNo = function(newObj) {
		globalVar.PrevQuestionNo = newObj;
	};
	var getPrevQuestionNo = function() {
		return globalVar.PrevQuestionNo;
	};
	var setUserName = function(newObj) {
		globalVar.userName = newObj;
	};
	var getUserName = function() {
		return globalVar.userName;
	};
	var setQuestionNo = function(newObj) {
		globalVar.QuestionNo = newObj;
	};
	var getQuestionNo = function() {
		return globalVar.QuestionNo;
	};
	var setTotalQuestions = function(newObj) {
		globalVar.totalQuestions = newObj;
	};
	var getTotalQuestions = function() {
		return globalVar.totalQuestions;
	};
	var setBeginTime = function(newObj) {
		globalVar.beginTime = newObj;
	};
	var getBeginTime = function() {
		return globalVar.beginTime;
	};
	var setTimerData = function(newObj) {
		globalVar.timerData = newObj;
	};
	var getTimerData = function() {
		return globalVar.timerData;
	};
	return {
		setAnswers   : setAnswers,
		getAnswers   : getAnswers,
		setPrevQuestionNo    : setPrevQuestionNo,
		getPrevQuestionNo    : getPrevQuestionNo,
		setTimerData : setTimerData,
		getTimerData : getTimerData,
		setUserName : setUserName,
		getUserName : getUserName,
		setQuestionNo : setQuestionNo,
		getQuestionNo : getQuestionNo,
		setTotalQuestions : setTotalQuestions,
		getTotalQuestions : getTotalQuestions,
		setBeginTime : setBeginTime,
		getBeginTime : getBeginTime
	};
});
//
mainApp.run(function($http, timerService, examService) {
	timerService.timeservice.startClock(function() {
		if (examService.getBeginTime()) {
			$http.post('/timer', examService.getTimerData())
				.then(function(response) {
					if (response.data.TimeRemain == 'TimeOut') {
						timerService.timeservice.stopClock();
					} else {
						timerService.timing.timeRem = response.data.TimeRemain;
					}
				}, function(response) {
					alert("Exception details: " + JSON.stringify({
							data : response.data
						}));
				});
		}
	});
});
mainApp.controller('HomeController', function($location) {
	$location.url('/login');
});
//