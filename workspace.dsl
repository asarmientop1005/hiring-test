workspace "Offer Inditex" "This is an workspace to illustrate the key features of Structurizr, via the DSL, based around a fictional online offer system." {

    model {
        customer = person "Personal Offer Customer" "A customer of the Inditex, with personal offer accounts." "Customer"

        softwareSystem = softwareSystem "Inditex Offer Management" "System to manage sales offers" {

            application = container "Application" "Contains application services" "Java" {

                offerApplicationService = component "OfferApplicationService" "Application service to manage offers" "Java"
            }

            domain = container "Domain" {
                description "Contains the domain components"
                technology "Java"

                offer = component "Offer" {
                    description "Domain entity that represents an offering"
                    technology "Java"
                }

                offerRepository = component "OfferRepository" {
                    description "Domain repository to manage offers"
                    technology "Java"
                }

                noSuchResourceFoundException = component "NoSuchResourceFoundException" {
                    description "Exception thrown when a resource is not found"
                    technology "Java"
                }

                offerService = component "OfferService" {
                    description "Domain service to manage the business logic of offers"
                    technology "Java"
                }
            }

            infrastructure = container "Infrastructure" {
                description "Contains the infrastructure components"
                technology "Java"

                offerConfiguration = component "OfferConfiguration" {
                    description "Application Settings"
                    technology "Java"
                }

                offerEntity = component "OfferEntity" {
                    description "JPA entity that represents an offer in the database"
                    technology "Java"
                }

                offerJpaRepository = component "OfferJpaRepository" {
                    description "JPA repository to access the database"
                    technology "Java"
                }

                offerRepositoryImpl = component "OfferRepositoryImpl" {
                    description "Implementation of domain repository using JPA"
                    technology "Java"
                }

                offerController = component "OfferController" {
                    description "REST controller to manage offers"
                    technology "Java"
                }

                offerDTO = component "OfferDTO" {
                    description "Data transfer object for offers"
                    technology "Java"
                }

                offerMapper = component "OfferMapper" {
                    description "Mapper to convert between entities and DTOs"
                    technology "Java"
                }

                globalExceptionHandler = component "GlobalExceptionHandler" {
                    description "Global exception handler"
                    technology "Java"
                }
            }

            database = container "Database" "Stores user registration offers" "H2 Database Schema" "Database"
        }

        customer -> offerApplicationService "Use"
        offerApplicationService -> offerService "Use"
        offerService -> offerRepository "Use"
        offerRepository -> offerRepositoryImpl "Implement"
        offerRepositoryImpl -> offerJpaRepository "Use"
        offerJpaRepository -> offerEntity "Manage"
        offerController -> offerApplicationService "Call"
        globalExceptionHandler -> offerController "Manage exceptions"
        offerMapper -> offerDTO "Convert"
        offerJpaRepository -> database "Reads from and writes to" "SQL/JDBC"
    }

    views {
        systemContext softwareSystem {
            include *
            autolayout lr
            animation {
                customer application
            }
        }

        container softwareSystem {
            include *
            autolayout lr
            animation {
                application domain
                domain infrastructure database
            }
        }

        component application {
            include *
            autolayout lr
            animation {
                offerApplicationService offerService
            }
        }

        component domain {
            include *
            autolayout lr
            animation {
                offerService offerRepository
            }
        }

        component infrastructure {
            include *
            autolayout lr
            animation {
                offerController offerApplicationService
                offerRepositoryImpl offerJpaRepository database
            }
        }

        theme default
        styles {
            element "Person" {
                color #ffffff
                fontSize 22
                shape Person
            }
            element "Customer" {
                background #08427b
            }
            element "Software System" {
                background #1168bd
                color #ffffff
            }
            element "Container" {
                background #438dd5
                color #ffffff
            }
            element "Database" {
                shape Cylinder
            }
            element "Component" {
                background #85bbf0
                color #000000
            }
        }
    }


}
