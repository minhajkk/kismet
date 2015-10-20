package org.kismet

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class KroinController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Kroin.list(params), model:[kroinCount: Kroin.count()]
    }

    def show(Kroin kroin) {
        respond kroin
    }

    def create() {
        respond new Kroin(params)
    }

    @Transactional
    def save(Kroin kroin) {
        if (kroin == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (kroin.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond kroin.errors, view:'create'
            return
        }

        kroin.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'kroin.label', default: 'Kroin'), kroin.id])
                redirect kroin
            }
            '*' { respond kroin, [status: CREATED] }
        }
    }

    def edit(Kroin kroin) {
        respond kroin
    }

    @Transactional
    def update(Kroin kroin) {
        if (kroin == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (kroin.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond kroin.errors, view:'edit'
            return
        }

        kroin.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'kroin.label', default: 'Kroin'), kroin.id])
                redirect kroin
            }
            '*'{ respond kroin, [status: OK] }
        }
    }

    @Transactional
    def delete(Kroin kroin) {

        if (kroin == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        kroin.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'kroin.label', default: 'Kroin'), kroin.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'kroin.label', default: 'Kroin'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
