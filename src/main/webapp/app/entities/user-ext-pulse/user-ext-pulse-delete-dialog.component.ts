import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserExtPulse } from 'app/shared/model/user-ext-pulse.model';
import { UserExtPulseService } from './user-ext-pulse.service';

@Component({
    selector: 'jhi-user-ext-pulse-delete-dialog',
    templateUrl: './user-ext-pulse-delete-dialog.component.html'
})
export class UserExtPulseDeleteDialogComponent {
    userExt: IUserExtPulse;

    constructor(private userExtService: UserExtPulseService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userExtService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'userExtListModification',
                content: 'Deleted an userExt'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-ext-pulse-delete-popup',
    template: ''
})
export class UserExtPulseDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userExt }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UserExtPulseDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.userExt = userExt;
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
