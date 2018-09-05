import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClientLeadPulse } from 'app/shared/model/client-lead-pulse.model';
import { ClientLeadPulseService } from './client-lead-pulse.service';

@Component({
    selector: 'jhi-client-lead-pulse-delete-dialog',
    templateUrl: './client-lead-pulse-delete-dialog.component.html'
})
export class ClientLeadPulseDeleteDialogComponent {
    clientLead: IClientLeadPulse;

    constructor(
        private clientLeadService: ClientLeadPulseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clientLeadService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'clientLeadListModification',
                content: 'Deleted an clientLead'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-client-lead-pulse-delete-popup',
    template: ''
})
export class ClientLeadPulseDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ clientLead }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ClientLeadPulseDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.clientLead = clientLead;
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
