/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PulseCheckAppTestModule } from '../../../test.module';
import { CategoryPulseDeleteDialogComponent } from 'app/entities/category-pulse/category-pulse-delete-dialog.component';
import { CategoryPulseService } from 'app/entities/category-pulse/category-pulse.service';

describe('Component Tests', () => {
    describe('CategoryPulse Management Delete Component', () => {
        let comp: CategoryPulseDeleteDialogComponent;
        let fixture: ComponentFixture<CategoryPulseDeleteDialogComponent>;
        let service: CategoryPulseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [CategoryPulseDeleteDialogComponent]
            })
                .overrideTemplate(CategoryPulseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CategoryPulseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoryPulseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
