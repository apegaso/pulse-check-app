import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionPulse } from 'app/shared/model/question-pulse.model';
import { QuestionPulseService } from './question-pulse.service';

@Component({
    selector: 'jhi-question-pulse-delete-dialog',
    templateUrl: './question-pulse-delete-dialog.component.html'
})
export class QuestionPulseDeleteDialogComponent {
    question: IQuestionPulse;

    constructor(private questionService: QuestionPulseService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.questionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'questionListModification',
                content: 'Deleted an question'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-question-pulse-delete-popup',
    template: ''
})
export class QuestionPulseDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ question }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(QuestionPulseDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.question = question;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
