import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionCategoryPulse } from 'app/shared/model/question-category-pulse.model';
import { QuestionCategoryPulseService } from './question-category-pulse.service';

@Component({
    selector: 'jhi-question-category-pulse-delete-dialog',
    templateUrl: './question-category-pulse-delete-dialog.component.html'
})
export class QuestionCategoryPulseDeleteDialogComponent {
    questionCategory: IQuestionCategoryPulse;

    constructor(
        private questionCategoryService: QuestionCategoryPulseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.questionCategoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'questionCategoryListModification',
                content: 'Deleted an questionCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-question-category-pulse-delete-popup',
    template: ''
})
export class QuestionCategoryPulseDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ questionCategory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(QuestionCategoryPulseDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.questionCategory = questionCategory;
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
