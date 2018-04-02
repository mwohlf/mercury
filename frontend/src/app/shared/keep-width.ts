import {AfterViewInit, Directive, ElementRef, HostListener, Renderer2} from "@angular/core";

@Directive({ selector: '[keepWidth]' })
export class KeepWidth implements AfterViewInit {

    constructor(
        private elementRef: ElementRef,
        private renderer: Renderer2
    ) { }

    public ngAfterViewInit(): void {
        console.log("width: " + this.elementRef.nativeElement.offsetWidth);
        let width = this.elementRef.nativeElement.offsetWidth;
        this.renderer.setStyle(this.elementRef.nativeElement,'max-width', width + "px");
        this.renderer.setStyle(this.elementRef.nativeElement,'width', width + "px");
        this.renderer.setStyle(this.elementRef.nativeElement,'min-width', width + "px");

    }
}
