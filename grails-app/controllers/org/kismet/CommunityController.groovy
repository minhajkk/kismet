package org.kismet

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CommunityController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Community.list(params), model:[communityCount: Community.count()]
    }

    def show(Community community) {
        respond community
    }

    def create() {
        respond new Community(params)
    }

    @Transactional
    def save(Community community) {
        if (community == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (community.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond community.errors, view:'create'
            return
        }

        community.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'community.label', default: 'Community'), community.id])
                redirect community
            }
            '*' { respond community, [status: CREATED] }
        }
    }

    def edit(Community community) {
        respond community
    }

    @Transactional
    def update(Community community) {
        if (community == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (community.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond community.errors, view:'edit'
            return
        }

        community.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'community.label', default: 'Community'), community.id])
                redirect community
            }
            '*'{ respond community, [status: OK] }
        }
    }

    @Transactional
    def delete(Community community) {

        if (community == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        community.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'community.label', default: 'Community'), community.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'community.label', default: 'Community'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
