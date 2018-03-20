var AppCtrl = angular.module('AppCtrl', []);
AppCtrl.controller('LoginController', function($scope, $http, $location, examService, timerService) {
	var self = this;
	$scope.loginParam = {
		headingTitle : "Candidate Login",
		Parameter : ''
	}
	self.headingTitle = "Candidate Login";
	$http.get('parameter/C0001').then(function(response) {
		$scope.loginParam.Parameter = response.data;
	})
	$scope.UserAuthentication = function() {
		var formData = {
			userName : $scope.userName,
			userPassword : $scope.userPassword
		};
		var successCallBack = function(response) {
			examService.setUserName(response.data.userName);
			examService.setQuestionNo(response.data.QuestionNo);
			examService.setTotalQuestions(response.data.TotalQuestion);
			timerService.timing.timeRem = response.data.TimeRemain;
			examService.setTimerData({
				"userName" : examService.getUserName()
			});
			examService.setBeginTime(true);
			console.log(response.data);
			$location.url('/question');
		};
		var errorCallBack = function(response) {
			alert("Exception details: " + JSON.stringify({
					data : response.data
				}));
		};
		$http.post('/login', formData).then(successCallBack, errorCallBack);
	}
});
//
AppCtrl.controller('QuestionNumberController', function($scope, $http, $route, examService) {
	var self = this;
	$scope.appData = {
		examTitle : "Online Test Exam Maker",
		Questionheader : ''
	}
	//examService.setQuestionNo('2');
	$scope.AnswerSelect = {
		defaultpicked : '1',
		AnswerList : []
	};
	//	
	$scope.QuestionAnswer = {
		QuestionDetails : '',
		QuestionNo : '',
		multipleNotSingle : false,
		Answers : []
	};
	//
	for (var i = 0; i < examService.getTotalQuestions(); i++) {
		$scope.AnswerSelect.AnswerList.push({
			"id" : i + 1
		})
	//$scope.AnswerSelect.AnswerList.push(angular.copy($scope.itemsToAdd))
	}

	var passCallBack = function(response) {
		console.log(response.data);
		if (response.data.QuestionNo == examService.getQuestionNo()) {
			if ((!angular.isUndefined(response.data.QuestionDetails)) && (response.data.QuestionDetails != null)) {
				console.log("The Question details is not null");
				$scope.QuestionAnswer.QuestionDetails = response.data.QuestionDetails.trim();
				$scope.QuestionAnswer.QuestionNo = response.data.QuestionNo;
				$scope.QuestionAnswer.multipleNotSingle = response.data.multipleNotSingle;
				angular.forEach(response.data.Answers, function(value, key) {
					$scope.QuestionAnswer.Answers.push({
						"id" : value.displayorder,
						"AnswerDetail" : value.answerchoice.trim(),
						"isChecked" : value.candidateanswer
					});
					console.log("Processed Item " + value.displayorder + ", " + value.answerchoice + ", " + value.candidateanswer);
				});
				$scope.AnswerSelect.defaultpicked = response.data.QuestionNo;
				$scope.appData.Questionheader = "Question " + $scope.QuestionAnswer.QuestionNo + " of " + examService.getTotalQuestions();
				console.log("QuestionNumberController :- " + $scope.appData.Questionheader + ", Default selection : " + $scope.AnswerSelect.defaultpicked);
				angular.forEach($scope.QuestionAnswer.Answers, function(value, key) {
					console.log("Answer Contents :- " + value.id + ", " + value.AnswerDetail + ", " + value.isChecked);
				});
			}
		}
	};
	var failCallBack = function(response) {
		alert("Exception details: " + JSON.stringify({
				data : response.data
			}));
	};
	var reqData = {
		userName : examService.getUserName(),
		questionNo : examService.getQuestionNo(),
		preQuestionNo : examService.getPrevQuestionNo(),
		Answers : examService.getAnswers()
	};
	console.log("Sending out Request for Question, Name, QNo, PreNo, Answers :- " + reqData.userName + ", " + reqData.questionNo + ", " + reqData.preQuestioNo + ", " + reqData.Answers);
	$http.post('/question/questionNo', reqData).then(passCallBack, failCallBack);

	$scope.getNextQuestion = function() {
		var tmpAnswers = [];
		if ((parseInt(examService.getQuestionNo()) + 1) <= (parseInt(examService.getTotalQuestions()))) {
			var xx = parseInt(examService.getQuestionNo()) + 1;
			examService.setPrevQuestionNo(examService.getQuestionNo());
			examService.setQuestionNo(xx.toString());
			angular.forEach($scope.QuestionAnswer.Answers, function(value, key) {
				tmpAnswers.push({
					"id" : value.id,
					"isChecked" : value.isChecked
				});
				console.log("Answers to be Returend to Server " + value.id + ", " + vflag);
			});
			examService.setAnswers(tmpAnswers);
			console.log("Next Question is :- " + examService.getQuestionNo());
			$route.reload();
		} else {
			console.log("Unable to get Next Question :- " + examService.getQuestionNo());
		}
	}
	$scope.getPreviousQuestion = function() {
		var tmpAnswers = [];
		if ((parseInt(examService.getQuestionNo()) - 1) > 0) {
			var xx = parseInt(examService.getQuestionNo()) - 1;
			examService.setPrevQuestionNo(examService.getQuestionNo());
			examService.setQuestionNo(xx.toString());
			angular.forEach($scope.QuestionAnswer.Answers, function(value, key) {
				tmpAnswers.push({
					"id" : value.id,
					"isChecked" : value.isChecked
				});
				console.log("Answers to be Returend to Server " + value.id + ", " + vflag);
			});
			examService.setAnswers(tmpAnswers);
			console.log("Previous Question is :- " + examService.getQuestionNo());
			$route.reload();
		} else {
			console.log("Unable to get Previous Question :- " + examService.getQuestionNo());
		}
	}
	$scope.getSelectedQuestion = function() {
		var tmpAnswers = [];
		if ((!angular.isUndefined($scope.AnswerSelect.defaultpicked)) && ($scope.AnswerSelect.defaultpicked != null) && ($scope.AnswerSelect.defaultpicked != '')) {
			var xx = parseInt(examService.getQuestionNo()) - 1;
			examService.setPrevQuestionNo(examService.getQuestionNo());
			examService.setQuestionNo($scope.AnswerSelect.defaultpicked);
			angular.forEach($scope.QuestionAnswer.Answers, function(value, key) {
				tmpAnswers.push({
					"id" : value.id,
					"isChecked" : value.isChecked
				});
				console.log("Answers to be Returend to Server " + value.id + ", " + vflag);
			});
			examService.setAnswers(tmpAnswers);
			console.log("Selected Question No is :- " + examService.getQuestionNo());
			$route.reload();
		} else {
			console.log("Unable to get Selected Question No :- " + examService.getQuestionNo());
		}
	}
});
//
AppCtrl.controller('EndExamController', function($scope, $http, examService) {
	//$scope.Questionheader = "Questions "+examService.getQuestionNo()+" of "+examService.getTotalQuestions();  
	//console.log("QuestionNumberController :- " + examService.getQuestionNo() + ", " + examService.getTotalQuestions());
	//$scope.examTitle = "Online Test Exam Maker";
});