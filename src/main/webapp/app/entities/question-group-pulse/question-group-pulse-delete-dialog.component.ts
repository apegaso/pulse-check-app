import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionGroupPulse } from 'app/shared/model/question-group-pulse.model';
import { QuestionGroupPulseService } from './question-group-pulse.service';

@Component({
    selector: 'jhi-question-group-pulse-delete-dialog',
    templateUrl: './question-group-pulse-delete-dialog.component.html'
})
export class QuestionGroupPulseDeleteDialogComponent {
    questionGroup: IQuestionGroupPulse;

    constructor(
        private questionGroupService: QuestionGroupPulseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.questionGroupService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'questionGroupListModification',
                content: 'Deleted an questionGroup'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-question-group-pulse-delete-popup',
    template: ''
})
export class QuestionGroupPulseDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ questionGroup }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(QuestionGroupPulseDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.questionGroup = questionGroup;
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
