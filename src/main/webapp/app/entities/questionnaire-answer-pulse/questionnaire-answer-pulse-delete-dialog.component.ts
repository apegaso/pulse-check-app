import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionnaireAnswerPulse } from 'app/shared/model/questionnaire-answer-pulse.model';
import { QuestionnaireAnswerPulseService } from './questionnaire-answer-pulse.service';

@Component({
    selector: 'jhi-questionnaire-answer-pulse-delete-dialog',
    templateUrl: './questionnaire-answer-pulse-delete-dialog.component.html'
})
export class QuestionnaireAnswerPulseDeleteDialogComponent {
    questionnaireAnswer: IQuestionnaireAnswerPulse;

    constructor(
        private questionnaireAnswerService: QuestionnaireAnswerPulseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.questionnaireAnswerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'questionnaireAnswerListModification',
                content: 'Deleted an questionnaireAnswer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-questionnaire-answer-pulse-delete-popup',
    template: ''
})
export class QuestionnaireAnswerPulseDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ questionnaireAnswer }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(QuestionnaireAnswerPulseDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.questionnaireAnswer = questionnaireAnswer;
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
