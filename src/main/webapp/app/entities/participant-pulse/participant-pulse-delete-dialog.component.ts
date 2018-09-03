import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParticipantPulse } from 'app/shared/model/participant-pulse.model';
import { ParticipantPulseService } from './participant-pulse.service';

@Component({
    selector: 'jhi-participant-pulse-delete-dialog',
    templateUrl: './participant-pulse-delete-dialog.component.html'
})
export class ParticipantPulseDeleteDialogComponent {
    participant: IParticipantPulse;

    constructor(
        private participantService: ParticipantPulseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.participantService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'participantListModification',
                content: 'Deleted an participant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-participant-pulse-delete-popup',
    template: ''
})
export class ParticipantPulseDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ participant }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ParticipantPulseDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.participant = participant;
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
