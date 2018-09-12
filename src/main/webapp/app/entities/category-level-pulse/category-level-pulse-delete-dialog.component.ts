import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategoryLevelPulse } from 'app/shared/model/category-level-pulse.model';
import { CategoryLevelPulseService } from './category-level-pulse.service';

@Component({
    selector: 'jhi-category-level-pulse-delete-dialog',
    templateUrl: './category-level-pulse-delete-dialog.component.html'
})
export class CategoryLevelPulseDeleteDialogComponent {
    categoryLevel: ICategoryLevelPulse;

    constructor(
        private categoryLevelService: CategoryLevelPulseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.categoryLevelService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'categoryLevelListModification',
                content: 'Deleted an categoryLevel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-category-level-pulse-delete-popup',
    template: ''
})
export class CategoryLevelPulseDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ categoryLevel }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CategoryLevelPulseDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.categoryLevel = categoryLevel;
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
