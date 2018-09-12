/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PulseCheckAppTestModule } from '../../../test.module';
import { CategoryLevelPulseDeleteDialogComponent } from 'app/entities/category-level-pulse/category-level-pulse-delete-dialog.component';
import { CategoryLevelPulseService } from 'app/entities/category-level-pulse/category-level-pulse.service';

describe('Component Tests', () => {
    describe('CategoryLevelPulse Management Delete Component', () => {
        let comp: CategoryLevelPulseDeleteDialogComponent;
        let fixture: ComponentFixture<CategoryLevelPulseDeleteDialogComponent>;
        let service: CategoryLevelPulseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [CategoryLevelPulseDeleteDialogComponent]
            })
                .overrideTemplate(CategoryLevelPulseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CategoryLevelPulseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoryLevelPulseService);
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
