/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LabsysTestModule } from '../../../test.module';
import { OrdenLabsysUpdateComponent } from 'app/entities/orden-labsys/orden-labsys-update.component';
import { OrdenLabsysService } from 'app/entities/orden-labsys/orden-labsys.service';
import { OrdenLabsys } from 'app/shared/model/orden-labsys.model';

describe('Component Tests', () => {
    describe('OrdenLabsys Management Update Component', () => {
        let comp: OrdenLabsysUpdateComponent;
        let fixture: ComponentFixture<OrdenLabsysUpdateComponent>;
        let service: OrdenLabsysService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [OrdenLabsysUpdateComponent]
            })
                .overrideTemplate(OrdenLabsysUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrdenLabsysUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdenLabsysService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OrdenLabsys(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orden = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OrdenLabsys();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orden = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
