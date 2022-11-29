import consult.Consult
import consult.ConsultConfirm
import consult.ConsultRegister
import explore.Explore
import explore.ExploreCategory
import explore.ExploreKeyword
import explore.ExploreUser
import home.Home
import mypage.Mypage
import mypage.MypageAnswer
import mypage.MypageQuestion
import profile.Profile
import question.Question
import question.QuestionRecommendation
import question.QuestionUser
import message.Messages
import message.Message
import react.VFC
import react.create
import react.router.Route
import react.router.Routes
import react.router.dom.HashRouter
import setting.Setting
import user.User


val ReactRouterDomApp = VFC {
    HashRouter {
        Routes {
            // home
            Route {
                path = "/"
                element = Home.create()
            }

            // consult
            Route {
                path = "consult/"

                Route {
                    path = ""
                    element = Consult.create()
                }

                Route {
                    path = "confirm/"
                    element = ConsultConfirm.create()
                }

                Route {
                    path = "register/"
                    element = ConsultRegister.create()
                }
            }

            // question
            Route {
                path = "question"

                Route {
                    path = ":questionId"
                    element = Question.create()
                }

                Route {
                    path = "recommendation/"
                    element = QuestionRecommendation.create()
                }

                Route {
                    path = ":userId"
                    element = QuestionUser.create()
                }
            }

            // explore
            Route {
                path = "explore/"

                Route {
                    path = ""
                    element = Explore.create()
                }

                Route {
                    path = "q/"
                    Route {
                        path = ":keyword"
                        element = ExploreKeyword.create()
                    }
                }

                Route {
                    path = "category/"
                    Route {
                        path = ":categoryId"
                        element = ExploreCategory.create()
                    }
                }

                Route {
                    path = "user/"
                    Route {
                        path = ":userId"
                        element = ExploreUser.create()
                    }
                }
            }

            // mypage
            Route {
                path = "mypage/"

                Route {
                    path = ""
                    element = Mypage.create()
                }

                Route {
                    path = "question/"
                    element = MypageQuestion.create()
                }

                Route {
                    path = "answer/"
                    element = MypageAnswer.create()
                }
            }

            // message
            Route {
                path = "message"

                Route {
                    path = ""
                    element = Messages.create()
                }

                Route {
                    path = ":userId"
                    element = Message.create()
                }
            }

            // user
            Route {
                path = "user/"
                Route {
                    path = ":userId"
                    element = User.create()
                }
            }

            // profile
            Route {
                path = "profile/"
                element = Profile.create()
            }

            // setting
            Route {
                path = "setting/"
                element = Setting.create()

            }
        }
    }
}