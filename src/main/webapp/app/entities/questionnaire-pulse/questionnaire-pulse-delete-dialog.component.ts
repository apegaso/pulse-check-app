import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionnairePulse } from 'app/shared/model/questionnaire-pulse.model';
import { QuestionnairePulseService } from './questionnaire-pulse.service';

@Component({
    selector: 'jhi-questionnaire-pulse-delete-dialog',
    templateUrl: './questionnaire-pulse-delete-dialog.component.html'
})
export class QuestionnairePulseDeleteDialogComponent {
    questionnaire: IQuestionnairePulse;

    constructor(
        private questionnaireService: QuestionnairePulseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.questionnaireService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'questionnaireListModification',
                content: 'Deleted an questionnaire'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-questionnaire-pulse-delete-popup',
    template: ''
})
export class QuestionnairePulseDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ questionnaire }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(QuestionnairePulseDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.questionnaire = questionnaire;
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
